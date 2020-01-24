package com.jonikoone.remotedriving.ui.mvp.presenters

import com.arellomobile.mvp.MvpPresenter
import com.jonikoone.remotedriving.ui.mvp.views.HomeView

class HomePresenter : MvpPresenter<HomeView>() {

    fun a () {
        viewState.createNewConnection()
    }

}