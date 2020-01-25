package com.jonikoone.remotedriving.app

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.jonikoone.remotedriving.db.ConnectionsDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        bind<ConnectionsDataBase>() with singleton {
            Room.databaseBuilder(applicationContext, ConnectionsDataBase::class.java, "testDB")
                .build()
        }
        bind<Cicerone<Router>>() with singleton { Cicerone.create() }
        //bind<NavigatorHolder>() with singleton { instance<Cicerone<Router>>().navigatorHolder }
//        import(ciceroneModule)

    }

    override fun onCreate() {
        super.onCreate()

        /*CoroutineScope(Dispatchers.IO).launch {
            //            ConnectionDataBaseSingltone.instance.connectionDao()
//                .insertConnection(ConnectionEntity(name = "test name", address = "10.0.2.2:8080"))
            Log.e("App", "insert to db")
            (instance<ConnectionsDataBase>() as ConnectionsDataBase).connectionDao().getConnections().forEach {
                Log.e("App", "get connection: $it")
            }
            Log.e("App", "end")
        }*/

    }

}
