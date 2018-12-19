package io.procrastination.weather.di.koin

import io.procrastination.weather.data.local.RoomWeatherRepository
import io.procrastination.weather.data.network.NetworkWeatherRepo
import io.procrastination.weather.domain.protocols.LocalWeatherRepository
import io.procrastination.weather.domain.protocols.WeatherRepository
import org.koin.dsl.module.module

val repositories = module {

    factory {
        NetworkWeatherRepo(
            get("weather-endpoint"),
            get("weather-key"),
            get("weather-icons")
        ) as WeatherRepository
    }

    factory {
        RoomWeatherRepository(get()) as LocalWeatherRepository
    }
}