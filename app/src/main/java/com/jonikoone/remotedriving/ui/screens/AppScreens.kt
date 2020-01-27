package com.jonikoone.remotedriving.ui.screens

import androidx.fragment.app.Fragment
import com.jonikoone.remotedriving.ui.fragments.ControlDriveFragment
import com.jonikoone.remotedriving.ui.fragments.CreateConnectionScreenFragment
import com.jonikoone.remotedriving.ui.fragments.HomeScreenFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

sealed class AppScreens(private val screenName: String, private val fragment: Fragment) :
    SupportAppScreen() {
    override fun getScreenKey() = screenName
    override fun getFragment() = fragment
}

class HomeScreen : AppScreens("Home screen", HomeScreenFragment())
class CreateConnectionScreen :
    AppScreens("Create connection screen", CreateConnectionScreenFragment())

class EditConnectionScreen(idConnection: Int) :
    AppScreens("Edit connection scren", CreateConnectionScreenFragment.newFragment(idConnection))

class ControlDriveScreen(name: String, address: String) :
    AppScreens("Control drive screen", ControlDriveFragment.newFragmet(name, address))