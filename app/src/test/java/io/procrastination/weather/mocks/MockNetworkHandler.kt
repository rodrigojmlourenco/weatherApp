package io.procrastination.weather.mocks

import io.procrastination.weather.domain.protocols.NetworkHandler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

class MockNetworkHandler : NetworkHandler {

    private var hasNetwork: Boolean = true
    private val mPublisher: PublishSubject<Boolean> = PublishSubject.create()

    fun setNetworkState(connected: Boolean): MockNetworkHandler {
        hasNetwork = connected
        mPublisher.onNext(hasNetwork)
        return this
    }

    override fun hasNetworkConnectivity(listener: Consumer<Boolean>): Disposable {
        return mPublisher.subscribe(listener)
    }

    override fun hasNetworkConnectivity(): Boolean {
        return hasNetwork
    }
}