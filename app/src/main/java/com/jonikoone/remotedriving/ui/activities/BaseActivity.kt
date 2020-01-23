package com.jonikoone.remotedriving.ui.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jonikoone.remotedriving.R
import com.jonikoone.remotedriving.ui.fragments.HomeScreenFragment

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        Log.e("BaseActivity", "onCreate")
        setContentView(R.layout.activity_base)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, HomeScreenFragment.newFreament(), "Home screen")
            .addToBackStack("home screen")
            .commitNow()
    }

}