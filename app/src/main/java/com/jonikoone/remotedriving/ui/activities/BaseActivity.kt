package com.jonikoone.remotedriving.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.ui.fragments.CreateConnectionScreen
import com.jonikoone.remotedriving.ui.fragments.HomeScreen
import com.jonikoone.remotedriving.ui.fragments.Screens
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

class BaseActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()

    val cicerone by instance<Cicerone<Router>>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("BaseActivity", "onCreate")
        setContentView(R.layout.activity_base)
        cicerone.router.newRootScreen(object : Screen() {
            override fun getScreenKey(): String {
                return "Home Screen"
            }
        })
        /*supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, HomeScreenFragment.newFreament(), "Home screen")
            .commitNow()*/
    }

    override fun onBackPressed() {
        cicerone.router.backTo()
    }

    /*val ciceroneModule = Kodein.Module(name = "ciceroneModule") {
        bind<Navigator>() with singleton {
            object : SupportAppNavigator(){

            }
        }
    }*/
}

