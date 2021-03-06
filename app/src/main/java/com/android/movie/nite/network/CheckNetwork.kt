package com.android.movie.nite.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi
import com.android.movie.nite.utils.Constants

class CheckNetwork(val context: Context) {
    @RequiresApi(Build.VERSION_CODES.N)
    fun registerNetworkCallback() {
        try {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerDefaultNetworkCallback(@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    Constants.isNetworkConnected = true // Global Static Variable
                }

                override fun onLost(network: Network) {
                    Constants.isNetworkConnected = false // Global Static Variable
                }
            }
            )
            Constants.isNetworkConnected = false
        } catch (e: Exception) {
            Constants.isNetworkConnected = false
        }
    }
}