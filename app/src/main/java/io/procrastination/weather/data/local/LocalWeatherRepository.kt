package io.procrastination.weather.data.local

import io.procrastination.weather.domain.model.WeatherInfo
import io.reactivex.Single

interface LocalWeatherRepository {

    fun cache(info : WeatherInfo) : Single<Boolean>

    fun getWeatherInfo(lat : Double, lng : Double) : Single<WeatherInfo>

    fun getWeatherInfo(city : String, zipCode : String? = null, country : String? = null) : Single<WeatherInfo>

    fun delete(info : WeatherInfo) : Single<Boolean>

    fun clear() : Single<Boolean>
}