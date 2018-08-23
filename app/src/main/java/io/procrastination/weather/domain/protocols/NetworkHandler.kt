package io.procrastination.weather.domain.protocols

import io.reactivex.functions.Consumer

interface NetworkHandler {
    fun hasNetworkConnectivity(listener : Consumer<Boolean>)
    fun hasNetworkConnectivity() : Boolean
}