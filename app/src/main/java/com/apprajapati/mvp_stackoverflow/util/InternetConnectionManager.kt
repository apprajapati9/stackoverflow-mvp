package com.apprajapati.mvp_stackoverflow.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService

class InternetConnectionManager(context: Context) {

    private val TAG = InternetConnectionManager::class.java.simpleName

    private lateinit var mNetworkListener: NetworkStatus

    fun setNetworkListener(listener: NetworkStatus) {
        this.mNetworkListener = listener
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        // network is available for use
        override fun onAvailable(network: Network) {
            Log.d(TAG, "Network available")
            Log.d(
                TAG,
                "Thread name-> ${Thread.currentThread().name}"
            )
            super.onAvailable(network)
            mNetworkListener.isNetworkAvailable(true)
        }

        // Network capabilities have changed for the network
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val unmetered =
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        }

        // lost network connection
        override fun onLost(network: Network) {
            super.onLost(network)
            Log.d(TAG, "Network lost!")
            mNetworkListener.isNetworkAvailable(false)
        }


    }

    private val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    private val connectivityManager =
        getSystemService(context, ConnectivityManager::class.java) as ConnectivityManager

    init {
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}

interface NetworkStatus {
    fun isNetworkAvailable(isIt: Boolean)
}