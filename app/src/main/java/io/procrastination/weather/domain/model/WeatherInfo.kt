package io.procrastination.weather.domain.model

import java.util.*

data class WeatherInfo
constructor(val condition : String,
            val temperature : Int,
            val windSpeed : Int,
            @field:Direction val windDirection : Int,
            val lastUpdatedAt : Date,
            val location : LocationInfo,
            val icon : String? = null){

    constructor(info: WeatherInfo, icon: String)
            : this(info.condition, info.temperature, info.windSpeed, info.windDirection, info.lastUpdatedAt, info.location, icon)
}