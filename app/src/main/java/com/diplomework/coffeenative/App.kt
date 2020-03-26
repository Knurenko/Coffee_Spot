package com.diplomework.coffeenative

import android.app.Application
import com.diplomework.coffeenative.data.repo.DataProvider
import com.diplomework.coffeenative.data.repo.PreferenceHelper

class App : Application() {

    override fun onCreate() {
        PreferenceHelper.init(this)
        DataProvider.loadDataFromSharedPrefs()
        super.onCreate()
    }

    override fun onTrimMemory(level: Int) {
        DataProvider.saveDataToSharedPrefs()
        super.onTrimMemory(level)
    }
}