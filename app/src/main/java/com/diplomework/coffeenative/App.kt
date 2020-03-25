package com.diplomework.coffeenative

import android.app.Application
import android.util.Log
import com.diplomework.coffeenative.data.repo.DataProvider
import com.diplomework.coffeenative.data.repo.PreferenceHelper

class App : Application() {

    override fun onCreate() {
        Log.e("check", "APP ON CREATE!")
        PreferenceHelper.init(this)
        DataProvider.loadDataFromSharedPrefs()
        super.onCreate()
    }

    override fun onTrimMemory(level: Int) {
        Log.e("check", "ON TRIM MEMORY")
        DataProvider.saveDataToSharedPrefs()
        super.onTrimMemory(level)
    }
}