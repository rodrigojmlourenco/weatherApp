package io.procrastination.weather.di.koin

import io.procrastination.weather.view.home.HomeNavigator
import io.procrastination.weather.view.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel

import org.koin.dsl.module.module

val home = module {
    viewModel { (navigator : HomeNavigator) ->
        val x = HomeViewModel()
        x.setNavigator(navigator)
        x.setNetworkHandler(get())
        x.setLocationHandler(get())
        x.setGetWeatherInfoUseCase(get())
        x
    }
}