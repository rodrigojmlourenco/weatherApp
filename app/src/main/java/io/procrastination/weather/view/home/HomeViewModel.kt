package io.procrastination.weather.view.home

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import io.procrastination.foundation.view.FoundationViewModel
import io.procrastination.foundation.view.containIn
import io.procrastination.weather.domain.UseCaseGetWeatherInfo
import io.procrastination.weather.domain.error.CachedInformationIsTooOldException
import io.procrastination.weather.domain.error.NoInformationAvailableToPresentToTheUserException
import io.procrastination.weather.domain.model.WeatherInfo
import io.procrastination.weather.domain.protocols.LocationHandler
import io.procrastination.weather.domain.protocols.NetworkHandler
import io.reactivex.functions.Consumer
import java.text.SimpleDateFormat
import java.util.Locale

class HomeViewModel : FoundationViewModel<HomeNavigator>() {

    private lateinit var getWeatherInfoUseCase: UseCaseGetWeatherInfo
    private lateinit var locationHandler: LocationHandler
    private lateinit var networkHandler: NetworkHandler

    val weatherInfo = MutableLiveData<WeatherInfo>()

    val location = ObservableField<String>()
    val condition = ObservableField<String>()
    val temperature = ObservableField<String>()
    val windspeed = ObservableField<String>()
    val windDirection = ObservableField<String>()
    val lastUpdate = ObservableField<String>()

    // State Observables
    val hasConnectivity = ObservableBoolean(true)
    val hasFreshData = ObservableBoolean(true)

    fun setGetWeatherInfoUseCase(useCase: UseCaseGetWeatherInfo) {
        this.getWeatherInfoUseCase = useCase
    }

    fun setLocationHandler(handler: LocationHandler) {
        this.locationHandler = handler
    }

    fun setNetworkHandler(handler: NetworkHandler) {
        this.networkHandler = handler
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        loadWeatherInfo()

        hasConnectivity.set(networkHandler.hasNetworkConnectivity())
        networkHandler.hasNetworkConnectivity(Consumer { hasConnectivity.set(it) }).containIn(usecaseContainer)
    }

    fun onPressedRefeshWeather() {

        if (hasConnectivity.get())
            loadWeatherInfo()
        else
            mNavigator.goToWifiSettings()
    }

    fun onPressedConnectWifi() {
        mNavigator.goToWifiSettings()
    }

    private fun loadWeatherInfo() {
        locationHandler.getUsersCurrentLocation(Consumer { location ->
            executeUseCaseInForeground(getWeatherInfoUseCase, location, Consumer { info ->
                weatherInfo.postValue(info)

                condition.set(info.condition)
                temperature.set("${info.temperature}ºc")
                windspeed.set("${info.windSpeed}mph")
                windDirection.set(mNavigator.getDirectionAsString(info.windDirection))
                lastUpdate.set(SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK).format(info.lastUpdatedAt))
                this.location.set("${info.location.city}, ${info.location.country}")
                hasFreshData.set(true)
            }, Consumer { error ->

                isLoading.postValue(false)

                when (error) {
                    is CachedInformationIsTooOldException -> hasFreshData.set(false)
                    is NoInformationAvailableToPresentToTheUserException -> hasFreshData.set(false)
                    else -> mNavigator.handleError(error)
                }
            })
        })
    }
}