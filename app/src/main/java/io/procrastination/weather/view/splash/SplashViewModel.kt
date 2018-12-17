package io.procrastination.weather.view.splash

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import io.procrastination.foundation.view.FoundationViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SplashViewModel : FoundationViewModel<SplashNavigator>() {

    @SuppressLint("CheckResult")
    override fun onStart(owner: LifecycleOwner) {
        Observable.just(true)
            .delay(DELAY_SPLASH, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { mNavigator.requestLocationPermissions() }
    }

    fun onPressedRequestPermissions() {
        mNavigator.requestLocationPermissions()
    }

    companion object {
        const val DELAY_SPLASH = 3L//Seconds
    }
}