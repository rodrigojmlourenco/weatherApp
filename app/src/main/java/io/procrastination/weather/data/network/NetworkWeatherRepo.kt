package io.procrastination.weather.data.network

import io.procrastination.foundation.data.BaseServiceGenerator
import io.procrastination.weather.domain.error.UnableToFetchWeatherInfo
import io.procrastination.weather.domain.model.WeatherInfo
import io.procrastination.weather.domain.protocols.WeatherRepository
import io.reactivex.Single
import okhttp3.Interceptor
import javax.net.ssl.HttpsURLConnection

open class NetworkWeatherRepo
constructor(
    baseUrl: String,
    private val apiKey: String,
    private val iconsUrl: String
) : BaseServiceGenerator<WeatherApi>(baseUrl), WeatherRepository {

    override val serviceClass: Class<WeatherApi>
        get() = WeatherApi::class.java

    override val interceptors: List<Interceptor>
        get() = emptyList()

    init {
        build()
    }

    override fun getWeatherInfo(lat: Double, lng: Double): Single<WeatherInfo> {

        return api().getWeatherByCoordinates(apiKey, lat.toString(), lng.toString())
            .map {
                if (it.code != HttpsURLConnection.HTTP_OK) {
                    throw UnableToFetchWeatherInfo()
                } else {
                    WeatherMapper(iconsUrl).toModel(it)
                }
            }
    }

    override fun getWeatherInfo(city: String, zipCode: String?, country: String?): Single<WeatherInfo> {

        val observable: Single<WeatherInfoDTO> = if (zipCode != null) {
            val correctedZip = zipCode + if (country != null) ",$country" else ""
            api().getWeatherByZipCode(apiKey, correctedZip)
        } else {
            val correctedCity = city + if (country != null) ",$country" else ""
            api().getWeatherByCityName(apiKey, correctedCity)
        }

        return observable.map {
            if (it.code != HttpsURLConnection.HTTP_OK) {
                throw UnableToFetchWeatherInfo()
            } else {
                WeatherMapper(iconsUrl).toModel(it)
            }
        }
    }
}