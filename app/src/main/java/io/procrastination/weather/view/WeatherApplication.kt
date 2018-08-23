package io.procrastination.weather.view

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.procrastination.weather.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class WeatherApplication : Application(), HasActivityInjector {

    @Inject lateinit var mActivityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var mTimberTree : Timber.Tree

    override fun onCreate() {
        super.onCreate()
        inject()

        Timber.plant(mTimberTree)
    }

    override fun activityInjector(): AndroidInjector<Activity> = mActivityInjector

    private fun inject(){
        DaggerAppComponent.builder().application(this).build().inject(this)
    }
}