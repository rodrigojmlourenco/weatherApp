package io.procrastination.weather.view.home

import io.procrastination.foundation.view.FoundationNavigator
import io.procrastination.foundation.view.FoundationViewModel
import io.procrastination.weather.domain.UseCaseGetWeatherInfo
import io.procrastination.weather.domain.protocols.LocationHandler

class HomeViewModel : FoundationViewModel<FoundationNavigator>(){

    private lateinit var getWeatherInfoUseCase: UseCaseGetWeatherInfo
    private lateinit var locationHandler: LocationHandler

    fun setGetWeatherInfoUseCase(useCase: UseCaseGetWeatherInfo) {
        this.getWeatherInfoUseCase = useCase
    }

    fun setLocationHandler(handler: LocationHandler){
        this.locationHandler = handler
    }

}