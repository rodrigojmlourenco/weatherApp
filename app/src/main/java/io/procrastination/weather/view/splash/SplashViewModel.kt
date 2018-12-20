package io.procrastination.weather.view.splash

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LifecycleOwner
import io.procrastination.foundation.view.FoundationViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashViewModel : FoundationViewModel<SplashNavigator>() {

    @SuppressLint("CheckResult")
    override fun onStart(owner: LifecycleOwner) {
        requestLocationPermissionsAfterDelay()
    }

    fun onPressedRequestPermissions() {
        mNavigator.requestLocationPermissions()
    }

    @VisibleForTesting
    private fun requestLocationPermissionsAfterDelay(seconds: Long = DELAY_SPLASH): Disposable {
        return Single.timer(seconds, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy { mNavigator.requestLocationPermissions() }
    }

    companion object {
        const val DELAY_SPLASH = 3L//Seconds
    }
}