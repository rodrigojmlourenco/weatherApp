package io.procrastination.weather.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import io.procrastination.foundation.domain.schedueler.Scheduler
import io.procrastination.weather.data.local.WeatherDatabase
import io.procrastination.weather.domain.protocols.LocationHandler
import io.procrastination.weather.domain.protocols.NetworkHandler
import io.procrastination.weather.view.handlers.ConnectivityManagerNetworkHandler
import io.procrastination.weather.view.handlers.FusedLocationHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    fun provideScheduler(): Scheduler {
        return object : Scheduler {
            override fun getSubscribeOn(): io.reactivex.Scheduler = Schedulers.io()

            override fun getObserveOn(): io.reactivex.Scheduler = AndroidSchedulers.mainThread()

            override fun getIoSubscribeOn(): io.reactivex.Scheduler = Schedulers.io()

            override fun getIoObserveOn(): io.reactivex.Scheduler = Schedulers.io()
        }
    }

    @Provides
    fun provideTimberTree(): Timber.Tree {
        return Timber.DebugTree()
    }

    @Provides
    fun provideLocationHandler(context: Context): LocationHandler {
        return FusedLocationHandler(context)
    }

    @Provides
    fun provideNetworkHandler(context: Context): NetworkHandler {
        return ConnectivityManagerNetworkHandler(context)
    }

    @Provides
    @Singleton
    fun provideWeatherDatabase(context: Context): WeatherDatabase {
        return Room.databaseBuilder(context, WeatherDatabase::class.java, "weather-db")
            .fallbackToDestructiveMigration()
            .build()
    }
}