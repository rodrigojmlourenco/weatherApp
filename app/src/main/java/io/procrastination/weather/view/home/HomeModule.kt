package io.procrastination.weather.view.home

import androidx.lifecycle.ViewModelProviders
import dagger.Module
import dagger.Provides
import io.procrastination.foundation.domain.schedueler.Scheduler
import io.procrastination.weather.domain.UseCaseGetWeatherInfo
import io.procrastination.weather.domain.protocols.LocationHandler
import io.procrastination.weather.domain.protocols.NetworkHandler
import io.procrastination.weather.view.handlers.FusedLocationHandler

@Module
class HomeModule {

    @Provides
    fun providesViewModel(
        view: HomeActivity,
        scheduler: Scheduler,
        useCase: UseCaseGetWeatherInfo,
        locationHandler: LocationHandler,
        networkHandler: NetworkHandler
    ): HomeViewModel {
        val vm = ViewModelProviders.of(view).get(HomeViewModel::class.java)

        vm.setNavigator(view)
        vm.setLifeCycleOwner(view)

        vm.setGetWeatherInfoUseCase(useCase)

        (locationHandler as? FusedLocationHandler)?.let { it.setupActivity(view) }
        vm.setLocationHandler(locationHandler)
        vm.setNetworkHandler(networkHandler)
        vm.scheduler = scheduler

        return vm
    }
}