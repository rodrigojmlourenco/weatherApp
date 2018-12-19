package io.procrastination.weather.di.koin

import io.procrastination.weather.domain.UseCaseGetWeatherInfo
import org.koin.dsl.module.module

val useCases = module {

    factory {
        UseCaseGetWeatherInfo(get(), get(), get(), get())
    }

}