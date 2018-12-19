package io.procrastination.weather.di.koin

import io.procrastination.weather.view.splash.SplashViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val splash = module {

    viewModel {
        SplashViewModel()
    }
}