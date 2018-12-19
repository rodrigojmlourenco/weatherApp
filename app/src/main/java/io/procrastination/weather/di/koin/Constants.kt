package io.procrastination.weather.di.koin

import io.procrastination.sample.R
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

val constants = module {

    single(name = "weather-endpoint") {
        "https://api.openweathermap.org/data/2.5/"
    }

    single(name = "weather-key") {
        androidContext().getString(R.string.weather_api)
    }

    single(name = "weather-icons") {
        "http://openweathermap.org/img/w/"
    }
}