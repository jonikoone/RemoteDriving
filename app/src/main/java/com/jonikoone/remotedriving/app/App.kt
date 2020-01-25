package com.jonikoone.remotedriving.app

import android.app.Application
import androidx.room.Room
import com.jonikoone.remotedriving.db.ConnectionsDataBase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import ru.terrakok.cicerone.Cicerone
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
        import(networkModule)
    }

    override fun onCreate() {
        super.onCreate()



    }

}
