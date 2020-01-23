package com.jonikoone.remotedriving.app

import android.app.Application
import android.util.Log
import com.jonikoone.remotedriving.db.ConnectionsDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/*
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ConnectionsDataBase.injectContext = applicationContext
        Log.e("App", "start")

        CoroutineScope(Dispatchers.IO).launch {
//            ConnectionDataBaseSingltone.instance.connectionDao()
//                .insertConnection(ConnectionEntity(name = "test name", address = "10.0.2.2:8080"))
            Log.e("App", "insert to db")
            ConnectionsDataBase.instance.connectionDao().getConnections().forEach {
                Log.e("App", "get connection: $it")
            }
            Log.e("App", "end")
        }

    }

}*/
