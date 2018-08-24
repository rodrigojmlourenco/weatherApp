package io.procrastination.weather.mocks

import com.google.gson.Gson
import io.procrastination.weather.data.network.WeatherInfoDTO
import io.procrastination.weather.data.network.WeatherMapper
import io.procrastination.weather.domain.model.LocationInfo
import io.procrastination.weather.domain.model.WeatherInfo


fun mockBaseUrl() : String = "https://samples.openweathermap.org/data/2.5/"
fun mockApiKey() : String = "b6907d289e10d714a6e88b30761fae22"
fun mockIconsUrl() : String = "http://openweathermap.org/img/w/"

fun mockTawaraLocation() : LocationInfo {
    return LocationInfo(35.02, 139.01, "Tawarano", "JP")
}

fun mockLondonLocation() : LocationInfo {
    return LocationInfo(51.51, -0.13, "London", "GB")
}


fun mockLondonWeatherInfo(timestamp : Long? = null) : WeatherInfo{

    val sample = "{\"coord\":{\"lon\":-0.13,\"lat\":51.51},\"weather\":[{\"id\":300,\"main\":\"Drizzle\",\"description\":\"light intensity drizzle\",\"icon\":\"09d\"}],\"base\":\"stations\",\"main\":{\"temp\":280.32,\"pressure\":1012,\"humidity\":81,\"temp_min\":279.15,\"temp_max\":281.15},\"visibility\":10000,\"wind\":{\"speed\":4.1,\"deg\":80},\"clouds\":{\"all\":90},\"dt\":1485789600,\"sys\":{\"type\":1,\"id\":5091,\"message\":0.0103,\"country\":\"GB\",\"sunrise\":1485762037,\"sunset\":1485794875},\"id\":2643743,\"name\":\"London\",\"cod\":200}"
    val dto = Gson().fromJson(sample, WeatherInfoDTO::class.java)

    val info = WeatherMapper(mockIconsUrl()).toModel(dto)

    timestamp?.let { return WeatherInfo(info, it) }
    return info
}

fun mockCoordsWeatherInfo(timestamp : Long? = null) : WeatherInfo{

    val sample = "{\"coord\":{\"lon\":139.01,\"lat\":35.02},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"base\":\"stations\",\"main\":{\"temp\":285.514,\"pressure\":1013.75,\"humidity\":100,\"temp_min\":285.514,\"temp_max\":285.514,\"sea_level\":1023.22,\"grnd_level\":1013.75},\"wind\":{\"speed\":5.52,\"deg\":311},\"clouds\":{\"all\":0},\"dt\":1485792967,\"sys\":{\"message\":0.0025,\"country\":\"JP\",\"sunrise\":1485726240,\"sunset\":1485763863},\"id\":1907296,\"name\":\"Tawarano\",\"cod\":200}"
    val dto = Gson().fromJson(sample, WeatherInfoDTO::class.java)

    val info = WeatherMapper(mockIconsUrl()).toModel(dto)

    timestamp?.let { return WeatherInfo(info, it) }
    return info
}




