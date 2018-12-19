package io.procrastination.weather.view

import android.app.Application
import io.procrastination.weather.di.koin.app
import io.procrastination.weather.di.koin.constants
import io.procrastination.weather.di.koin.home
import io.procrastination.weather.di.koin.repositories
import io.procrastination.weather.di.koin.splash
import io.procrastination.weather.di.koin.useCases
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin(this, listOf(app, constants, repositories, useCases, splash, home))
    }

}