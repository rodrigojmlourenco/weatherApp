package io.procrastination.weather.domain.repositories

import io.procrastination.weather.domain.model.WeatherInfo
import io.reactivex.Single

interface WeatherRepository {

    fun getWeatherInfo(lat : Double, lng : Double) : Single<WeatherInfo>

    fun getWeatherInfo(city : String, zipCode : String? = null, country : String? = null) : Single<WeatherInfo>

}