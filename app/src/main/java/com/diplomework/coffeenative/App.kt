package com.diplomework.coffeenative

import android.app.Application
import com.diplomework.coffeenative.data.network.NetworkStateReceiver
import com.diplomework.coffeenative.data.repo.DataProvider
import com.diplomework.coffeenative.data.repo.PreferenceHelper
import com.diplomework.coffeenative.data.repo.Statistics

class App : Application() {

    override fun onCreate() {
        PreferenceHelper.init(this)
        DataProvider.isNetworkOnline = NetworkStateReceiver.getConnectivityStatus(this)
        DataProvider.loadDataFromSharedPrefs()
        Statistics.calc()
        super.onCreate()
    }

    override fun onTrimMemory(level: Int) {
        DataProvider.saveDataToSharedPrefs()
        super.onTrimMemory(level)
    }
}