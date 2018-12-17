package io.procrastination.weather.data.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherInfoDTO
constructor(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("cod") val code: Int,
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("dt") val timestamp: String,
    @Expose @SerializedName("base") val base: String,
    @Expose @SerializedName("coord") val coordinates: CoordinateDTO,
    @Expose @SerializedName("weather") val weather: List<WeatherItemDTO>,
    @Expose @SerializedName("main") val main: WeatherConditionsDTO,
    @Expose @SerializedName("wind") val wind: WindInfoDTO,
    @Expose @SerializedName("clouds") val clouds: CloudsInfoDTO,
    @Expose @SerializedName("sys") val sys: SystemsInfoDTO
)

data class CoordinateDTO
constructor(
    @Expose @SerializedName("lon") val longitude: Double,
    @Expose @SerializedName("lat") val latitude: Double
)

data class WeatherItemDTO
constructor(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("main") val main: String,
    @Expose @SerializedName("description") val description: String,
    @Expose @SerializedName("icon") val icon: String
)

data class WeatherConditionsDTO
constructor(
    @Expose @SerializedName("temp") val temp: Double,
    @Expose @SerializedName("pressure") val pressure: Double,
    @Expose @SerializedName("humidity") val humidity: Int,
    @Expose @SerializedName("temp_min") val minTemp: Double,
    @Expose @SerializedName("temp_max") val maxTemp: Double,
    @Expose @SerializedName("sea_level") val seaLevel: Double,
    @Expose @SerializedName("grnd_level") val groundLevel: Double
)

data class WindInfoDTO
constructor(
    @Expose @SerializedName("speed") val speed: Float,
    @Expose @SerializedName("deg") val degrees: Float
)

data class CloudsInfoDTO
constructor(@Expose @SerializedName("all") val all: Int)

data class SystemsInfoDTO
constructor(
    @Expose @SerializedName("message") val message: Float,
    @Expose @SerializedName("country") val country: String?
)