package com.diplomework.coffeenative.data.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import com.diplomework.coffeenative.data.repo.DataProvider

class NetworkStateReceiver : BroadcastReceiver() {

    companion object {

        fun getConnectivityStatus(context: Context): Boolean {
            val cm = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = cm.activeNetworkInfo
            if (null != activeNetwork) {
                if (activeNetwork.type == ConnectivityManager.TYPE_WIFI) return true
                if (activeNetwork.type == ConnectivityManager.TYPE_MOBILE) return true
            }
            return false
        }
    }

    override fun onReceive(p0: Context?, p1: Intent?) {

        if (p1?.action == ConnectivityManager.CONNECTIVITY_ACTION || p1?.action == WifiManager.WIFI_STATE_CHANGED_ACTION) {
            DataProvider.isNetworkOnline = getConnectivityStatus(p0!!)
        }
    }
}