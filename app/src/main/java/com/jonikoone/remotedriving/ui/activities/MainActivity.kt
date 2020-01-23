package com.jonikoone.remotedriving.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.jonikoone.remotedriving.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.net.InetAddress
import java.net.Socket
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main


    val socket = async(Dispatchers.IO) {
        Socket(InetAddress.getByAddress(byteArrayOf(10,0,2,2)), 9999)
    }

    val messager = Messager(socket)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launch(Dispatchers.IO) {
            messager.start(messageSender = { message, out ->
                //правила отправки сообщения
                out.writeObject(message)
            }, postStartProcess = {
                connectionInfo.text = "connect to server OK"
            }, preStartProcess = {
                connectionInfo.text = "start"
            })
        }

        btnUp.setOnClickListener {
            launch(Dispatchers.IO) {
                withContext(Dispatchers.Main) {
                    connectionInfo.text = "UP sending..."
                }
                messager.send("UP".toMessage())
                withContext(Dispatchers.Main) {
                    connectionInfo.text = "UP sending...OK"
                }
            }
        }

    }


}

class Messager(
    val socketDeferred: Deferred<Socket>
) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    val TAG = "Messager"
    lateinit var outStream: ObjectOutputStream
    lateinit var inStream: ObjectInputStream

    private var messageSender: suspend (Message, ObjectOutputStream) -> Unit =
        { message: Message, outputStream: ObjectOutputStream ->
            Log.e(TAG, "not init notwork streams!")
        }

    private var messageReciver = {

    }

    suspend fun start(
        messageSender: suspend (Message, ObjectOutputStream) -> Unit = this.messageSender,
        messageReciver: () -> Unit = this.messageReciver,
        postStartProcess: () -> Unit,
        preStartProcess: () -> Unit
    ) {
        Log.e(TAG, "start messager")
        withContext(Dispatchers.Main) {
            Log.e(TAG,"preStartProcess")
            preStartProcess.invoke()
        }
        socketDeferred.await().let {
            Log.e(TAG, "get socket, create streams")
            outStream = ObjectOutputStream(it.getOutputStream())
            Log.e(TAG, "Output Stream created")
//            inStream = ObjectInputStream(it.getInputStream())

        }
        this.messageSender = messageSender
        //месседжер стартанул
        withContext(Dispatchers.Main) {
            Log.e(TAG,"postStartProcess")
            postStartProcess.invoke()
        }

    }

    suspend fun send(message: Message) {
        messageSender.invoke(message, outStream)
    }

}

data class Message(
    val message: String = ""
) : Serializable {
    companion object {
        private const val serialVersionUID = 123L
    }
}

fun String.toMessage(): Message {
    return Message(this)
}