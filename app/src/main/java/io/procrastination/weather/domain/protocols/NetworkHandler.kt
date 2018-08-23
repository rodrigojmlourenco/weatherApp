package io.procrastination.weather.domain.protocols

import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

interface NetworkHandler {
    fun hasNetworkConnectivity(listener : Consumer<Boolean>) : Disposable
    fun hasNetworkConnectivity() : Boolean
}