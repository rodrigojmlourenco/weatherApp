package io.procrastination.weather.view.home

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import io.procrastination.foundation.view.FoundationViewModel
import io.procrastination.weather.domain.UseCaseGetWeatherInfo
import io.procrastination.weather.domain.model.WeatherInfo
import io.procrastination.weather.domain.protocols.LocationHandler
import io.reactivex.functions.Consumer
import java.text.SimpleDateFormat

class HomeViewModel : FoundationViewModel<HomeNavigator>(){

    private lateinit var getWeatherInfoUseCase: UseCaseGetWeatherInfo
    private lateinit var locationHandler: LocationHandler

    val weatherInfo = MutableLiveData<WeatherInfo>()

    val location = ObservableField<String>()
    val condition = ObservableField<String>()
    val temperature = ObservableField<String>()
    val windspeed = ObservableField<String>()
    val windDirection = ObservableField<String>()
    val lastUpdate = ObservableField<String>()

    fun setGetWeatherInfoUseCase(useCase: UseCaseGetWeatherInfo) {
        this.getWeatherInfoUseCase = useCase
    }

    fun setLocationHandler(handler: LocationHandler){
        this.locationHandler = handler
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        loadWeatherInfo()
    }

    fun onPressedRefeshWeather(){
        loadWeatherInfo()
    }

    fun onPressedConnectWifi(){
        mNavigator.goToWifiSettings()
    }

    private fun loadWeatherInfo(){
        locationHandler.getUsersCurrentLocation(Consumer { location ->
            executeUseCaseInForeground(getWeatherInfoUseCase, location, Consumer {info ->
                weatherInfo.postValue(info)

                condition.set(info.condition)
                temperature.set("${info.temperature}ºc")
                windspeed.set("${info.windSpeed}mph")
                windDirection.set(mNavigator.getDirectionAsString(info.windDirection))
                lastUpdate.set(SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(info.lastUpdatedAt))
                this.location.set("${info.location.city}, ${info.location.country}")
            })
        })
    }
}