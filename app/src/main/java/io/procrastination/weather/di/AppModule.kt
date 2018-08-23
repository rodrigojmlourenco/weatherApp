package io.procrastination.weather.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.procrastination.foundation.domain.schedueler.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Singleton

@Module
class AppModule {

    @Provides @Singleton
    fun provideContext(application : Application) : Context {
        return application.applicationContext
    }

    @Provides
    fun provideScheduler() : Scheduler {
        return object : Scheduler {
            override fun getSubscribeOn(): io.reactivex.Scheduler = Schedulers.io()

            override fun getObserveOn(): io.reactivex.Scheduler = AndroidSchedulers.mainThread()

            override fun getIoSubscribeOn(): io.reactivex.Scheduler = Schedulers.io()

            override fun getIoObserveOn(): io.reactivex.Scheduler = Schedulers.io()
        }
    }

    @Provides
    fun provideTimberTree() : Timber.Tree {
        return Timber.DebugTree()
    }
}