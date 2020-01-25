package com.jonikoone.remotedriving.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.db.ConnectionsDataBase
import com.jonikoone.remotedriving.db.entites.ConnectionEntity
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import kotlin.coroutines.CoroutineContext

class CreateConnectionScreenFragment: Fragment(), CoroutineScope, KodeinAware {
    override val kodein by kodein()
    override val coroutineContext = Dispatchers.Main

    private val database by instance<ConnectionsDataBase>()
    private val cicerone by instance<Cicerone<Router>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_connection_screen, container, false)

        //TODO: перенесу после изучения moxy или других библиотек для MV(P,I,VM..)
        val txtIpAddress = view.findViewById<TextView>(R.id.txtIpAddress)!!
        val txtIpPort = view.findViewById<TextView>(R.id.txtIpPort)!!
        val txtNameConnection = view.findViewById<TextView>(R.id.txtNameConnection)

        view.findViewById<Button>(R.id.btnTryNewConnection)?.apply {
            setOnClickListener {
                //checkRemoveHost()
            }
        }

        view.findViewById<Button>(R.id.btnSaveConnetion)?.apply {
            setOnClickListener {
                launch (Dispatchers.IO) {
                    val newConnection = ConnectionEntity(name = txtNameConnection.text.toString(), address = "${txtIpAddress.text}:${txtIpPort.text}")
                    checkRemoveHost(newConnection)
                    database.connectionDao().insertConnection(newConnection)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Connection save - OK", Toast.LENGTH_SHORT).show()
                        delay(1000)
                        cicerone.router.exit()
                    }

                }
            }
        }

        return view
    }

    suspend fun checkRemoveHost(connectionEntity: ConnectionEntity): Boolean = runBlocking {
        //проверка удаленного узла при сохранении
        true
    }

}