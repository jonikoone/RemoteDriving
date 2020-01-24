package com.jonikoone.remotedriving.ui.mvp.views

import com.arellomobile.mvp.MvpView

interface HomeView : MvpView {
    fun createNewConnection()
    fun openConnection()
}