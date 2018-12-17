package io.procrastination.weather.view.splash

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import io.procrastination.foundation.view.FoundationViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class SplashViewModel : FoundationViewModel<SplashNavigator>() {

    @SuppressLint("CheckResult")
    override fun onStart(owner: LifecycleOwner) {
        start().subscribe()
    }

    internal fun start(delay : Long = DELAY_SPLASH) : Observable<Boolean> {
        return Observable.just(true)
                .delay(delay, TimeUnit.SECONDS)
                .subscribeOn(scheduler.getSubscribeOn())
                .observeOn(scheduler.getObserveOn())
                .doOnComplete { onPressedRequestPermissions() }
    }

    fun onPressedRequestPermissions() {
        mNavigator.requestLocationPermissions()
    }

    companion object {
        const val DELAY_SPLASH = 3L//Seconds
    }
}