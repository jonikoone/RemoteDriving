package com.jonikoone.remotedriving.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.services.ServiceAPI
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.CoroutineContext

class Main2Activity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    val gson = GsonBuilder().setLenient().create()
    val okHttpClient = OkHttpClient.Builder().build()

    lateinit var serviceAPI: ServiceAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        connect.setOnClickListener {
            val address = addresField?.text.toString()
            val retrofit = Retrofit.Builder()
                .baseUrl("http://$address/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            serviceAPI = retrofit.create(ServiceAPI::class.java)
            requestMessage("init connection")

            cmd1.isEnabled = true
            cmd2.isEnabled = true

            /*joystick.callback = {
                launch(Dispatchers.IO) {
                    serviceAPI.sendPosition(it).execute()
                }
            }*/

            touchScreen.onSendPosotion = {
                //            Log.e("position", it.toString())
                launch(Dispatchers.IO) {
                    serviceAPI.sendPosition(it).execute()
                }
            }
        }
        cmd1.setOnClickListener {
            requestMessage("cmd1")
        }
        cmd2.setOnClickListener {
            requestMessage("cmd2")
        }

    }

    fun requestMessage(message: String) = CoroutineScope(Dispatchers.IO).launch {
        val call = serviceAPI.sendCommand(message)
        val body = call.execute().body()
        withContext(Dispatchers.Main) {
            log.append("\n$body")
        }
    }

}


