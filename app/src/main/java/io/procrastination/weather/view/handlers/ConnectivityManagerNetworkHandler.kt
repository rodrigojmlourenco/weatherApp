package io.procrastination.weather.view.handlers

import android.content.Context
import android.net.ConnectivityManager
import io.procrastination.weather.domain.protocols.NetworkHandler
import io.reactivex.functions.Consumer

class ConnectivityManagerNetworkHandler(context : Context) : NetworkHandler{

    override fun hasNetworkConnectivity(): Boolean {
        val activeNetworkInfo = mConnectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private val mConnectivityManager : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun hasNetworkConnectivity(listener: Consumer<Boolean>) {
        val activeNetworkInfo = mConnectivityManager.activeNetworkInfo
        listener.accept( activeNetworkInfo != null && activeNetworkInfo.isConnected)
    }

}