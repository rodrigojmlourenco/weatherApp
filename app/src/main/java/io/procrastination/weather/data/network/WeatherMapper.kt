package io.procrastination.weather.data.network

import io.procrastination.foundation.data.DTOMapper
import io.procrastination.weather.domain.model.Direction
import io.procrastination.weather.domain.model.EAST
import io.procrastination.weather.domain.model.LocationInfo
import io.procrastination.weather.domain.model.NORTH
import io.procrastination.weather.domain.model.NORTH_EAST
import io.procrastination.weather.domain.model.NORTH_WEST
import io.procrastination.weather.domain.model.SOUTH
import io.procrastination.weather.domain.model.SOUTH_EAST
import io.procrastination.weather.domain.model.SOUTH_WEST
import io.procrastination.weather.domain.model.WEST
import io.procrastination.weather.domain.model.WeatherInfo
import java.util.Date

class WeatherMapper(private val iconBaseUrl: String) : DTOMapper<WeatherInfoDTO, WeatherInfo> {

    override fun toModel(dto: WeatherInfoDTO): WeatherInfo {

        return WeatherInfo(
            dto.weather[0].main,
            dto.main.temp.toInt(),
            dto.wind.speed.toInt(),
            degreeToCompassDirection(dto.wind),
            Date(dto.timestamp.toLong() * 1000),
            LocationInfo(dto.coordinates.latitude, dto.coordinates.longitude, dto.name, dto.sys.country),
            buildIconUrl(dto)
        )
    }

    @Direction
    private fun degreeToCompassDirection(windInfo: WindInfoDTO): Int {

        val deg = windInfo.degrees

        // TODO: the degree mapping is not correct
        return when {
            deg >= 45 && deg < 90 -> NORTH_EAST
            deg >= 90 && deg < 135 -> EAST
            deg >= 135 && deg < 180 -> SOUTH_EAST
            deg >= 180 && deg < 225 -> SOUTH
            deg >= 225 && deg < 270 -> SOUTH_WEST
            deg >= 270 && deg < 315 -> WEST
            deg >= 315 && deg < 360 -> NORTH_WEST
            else -> NORTH
        }
    }

    private fun buildIconUrl(dto: WeatherInfoDTO): String? {
        return if (dto.weather.isNotEmpty())
            "$iconBaseUrl${dto.weather[0].icon}.png"
        else
            null
    }
}