package io.procrastination.weather.view.splash

import android.arch.lifecycle.LifecycleOwner
import io.procrastination.foundation.view.FoundationViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class SplashViewModel : FoundationViewModel<SplashNavigator>(){

    override fun onStart(owner: LifecycleOwner) {
        Observable.just(true)
                .delay(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { mNavigator.requestLocationPermissions() }
    }

    fun onPressedRequestPermissions(){
        mNavigator.requestLocationPermissions()
    }
}