package io.procrastination.weather.data.local

import io.procrastination.weather.domain.model.LocationInfo
import io.procrastination.weather.domain.model.WeatherInfo
import io.procrastination.weather.domain.protocols.Mapper
import java.util.Date

class CachedWeatherMapper : Mapper<WeatherInfo, CachedWeatherInfo> {

    override fun fromModel(model: WeatherInfo): CachedWeatherInfo {
        return CachedWeatherInfo(
            generatePrimaryKey(model),
            model.condition,
            model.temperature,
            model.windSpeed,
            model.windDirection,
            model.lastUpdatedAt.time,
            model.location.latitude,
            model.location.longitude,
            model.location.city,
            model.location.country,
            model.location.zipCode,
            model.icon
        )
    }

    override fun toModel(cachedItem: CachedWeatherInfo): WeatherInfo {
        return WeatherInfo(
            cachedItem.conditions,
            cachedItem.temperature,
            cachedItem.windSpeed,
            cachedItem.windDirecton,
            Date(cachedItem.timestamp),
            LocationInfo(
                cachedItem.latitude,
                cachedItem.longitude,
                cachedItem.city,
                cachedItem.countryCode,
                cachedItem.zipCode
            ),
            cachedItem.icon
        )
    }

    private fun generatePrimaryKey(item: WeatherInfo): String {
        return "${item.location.city}${item.location.country}"
    }
}