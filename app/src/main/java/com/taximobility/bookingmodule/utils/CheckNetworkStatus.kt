package com.taximobility.bookingmodule.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.taximobility.util.TaxiUtil


class CheckNetworkStatus : BroadcastReceiver() {

    private val NETWORK_AVAILABLE_ACTION = TaxiUtil.ACTIVITY_ACTION
    private val IS_NETWORK_AVAILABLE = "isNetworkAvailable"


    override fun onReceive(context: Context, p1: Intent?) {
        val networkStateIntent = Intent(NETWORK_AVAILABLE_ACTION)
        networkStateIntent.putExtra(IS_NETWORK_AVAILABLE, isConnectedToInternet(context))
        LocalBroadcastManager.getInstance(context).sendBroadcast(networkStateIntent)
    }

    private fun isConnectedToInternet(context: Context?): Boolean {
        try {
            if (context != null) {
                val connectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            }
            return false
        } catch (e: Exception) {
            return false
        }
    }
}