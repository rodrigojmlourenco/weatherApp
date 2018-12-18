package io.procrastination.weather

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.HasActivityInjector

class TestWeatherApp : Application(), HasActivityInjector {


    override fun activityInjector(): AndroidInjector<Activity> {
        return AndroidInjector {}
    }
}