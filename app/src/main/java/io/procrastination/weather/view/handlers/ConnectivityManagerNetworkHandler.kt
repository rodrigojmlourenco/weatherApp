package io.procrastination.weather.view.handlers

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import io.procrastination.weather.domain.protocols.NetworkHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

class ConnectivityManagerNetworkHandler(context: Context) : ConnectivityManager.NetworkCallback(), NetworkHandler {

    private val mNetworkPublisher: PublishSubject<Boolean> = PublishSubject.create()
    private val mConnectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
            .build()

        mConnectivityManager.registerNetworkCallback(request, this)
    }

    override fun hasNetworkConnectivity(): Boolean {
        val activeNetworkInfo = mConnectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun hasNetworkConnectivity(listener: Consumer<Boolean>): Disposable {
        return mNetworkPublisher.observeOn(AndroidSchedulers.mainThread()).subscribe(listener)
    }

    override fun onAvailable(network: Network?) {
        super.onAvailable(network)
        mNetworkPublisher.onNext(true)
    }

    override fun onLost(network: Network?) {
        super.onLost(network)
        mNetworkPublisher.onNext(false)
    }
}