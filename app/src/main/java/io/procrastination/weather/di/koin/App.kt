package io.procrastination.weather.di.koin

import androidx.room.Room
import io.procrastination.foundation.domain.schedueler.Scheduler
import io.procrastination.weather.data.local.WeatherDatabase
import io.procrastination.weather.domain.protocols.LocationHandler
import io.procrastination.weather.domain.protocols.NetworkHandler
import io.procrastination.weather.view.handlers.ConnectivityManagerNetworkHandler
import io.procrastination.weather.view.handlers.FusedLocationHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import timber.log.Timber

val app = module {

    factory {
        object : Scheduler {
            override fun getSubscribeOn(): io.reactivex.Scheduler = Schedulers.io()

            override fun getObserveOn(): io.reactivex.Scheduler = AndroidSchedulers.mainThread()

            override fun getIoSubscribeOn(): io.reactivex.Scheduler = Schedulers.io()

            override fun getIoObserveOn(): io.reactivex.Scheduler = Schedulers.io()
        } as Scheduler
    }

    factory {
        Timber.DebugTree() as Timber.Tree
    }

    factory {
        FusedLocationHandler(androidContext()) as LocationHandler
    }

    factory {
        ConnectivityManagerNetworkHandler(androidContext()) as NetworkHandler
    }

    factory {
        Room.databaseBuilder(androidContext(), WeatherDatabase::class.java, "weather-db")
            .fallbackToDestructiveMigration()
            .build()
    }
}