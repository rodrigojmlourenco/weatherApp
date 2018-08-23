package io.procrastination.weather.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API contract for [OpenWeatherMap]("https://openweathermap.org/current").
 */
interface WeatherApi {

    /**
     * You can call by city name or city name and country code. API responds with a list of results
     * that match a searching word.
     * <br>
     * There is a possibility to receive a central district of the city/town with its own parameters
     * (geographic coordinates/id/name) in API response.
     */
    @GET("weather")
    fun getWeatherByCityName(@Query("appid") api : String,
                             @Query("q") city : String) : Single<WeatherInfoDTO>


    @GET("weather")
    fun getWeatherByCoordinates(@Query("appid") api : String,
                                @Query("lat") lat : String,
                                @Query("lon") lon : String) : Single<WeatherInfoDTO>

    /**
     * Please note if country is not specified then the search works for USA as a default.
     */
    @GET("weather")
    fun getWeatherByZipCode(@Query("appid") api : String,
                            @Query("zip") zipCode : String) : Single<WeatherInfoDTO>

    /**
    * You can call by city ID. API responds with exact result.
    *
    * List of city ID city.list.json.gz can be downloaded here http://bulk.openweathermap.org/sample/
    */
    @GET("weather")
    fun getWeatherByCityId(@Query("appid") api : String,
                           @Query("zip") zip : String) : Single<WeatherInfoDTO>
}