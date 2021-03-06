package com.jonikoone.remotedriving.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.ui.screens.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen

class BaseActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()

    val cicerone by instance<Cicerone<Router>>()

    val navigator = object : SupportAppNavigator(this, R.id.fragmentContainer){
        override fun createFragment(screen: SupportAppScreen?): Fragment {
            screen as AppScreens
            return when (screen) {
                is HomeScreen -> screen.fragment
                is CreateConnectionScreen -> screen.fragment
                is EditConnectionScreen -> screen.fragment
                is ControlDriveScreen -> screen.fragment
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        cicerone.router.newRootScreen(HomeScreen())
    }

    override fun onResume() {
        super.onResume()
        cicerone.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        cicerone.navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        cicerone.router.exit()
    }

}

