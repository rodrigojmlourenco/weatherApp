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

/**
 * TODO: Improve degree mapping
 */
class WeatherMapper(private val iconBaseUrl: String) : DTOMapper<WeatherInfoDTO, WeatherInfo> {

    override fun toModel(dto: WeatherInfoDTO): WeatherInfo {

        return WeatherInfo(
            dto.weather[0].main,
            dto.main.temp.toInt(),
            dto.wind.speed.toInt(),
            degreeToCompassDirection(dto.wind),
            Date(dto.timestamp.toLong() * CONVERSION_FACTOR),
            LocationInfo(dto.coordinates.latitude, dto.coordinates.longitude, dto.name, dto.sys.country),
            buildIconUrl(dto)
        )
    }

    @Direction
    private fun degreeToCompassDirection(windInfo: WindInfoDTO): Int {

        val deg = windInfo.degrees

        return when {
            deg >= DEGREE_45 && deg < DEGREE_90 -> NORTH_EAST
            deg >= DEGREE_90 && deg < DEGREE_135 -> EAST
            deg >= DEGREE_135 && deg < DEGREE_180 -> SOUTH_EAST
            deg >= DEGREE_180 && deg < DEGREE_225 -> SOUTH
            deg >= DEGREE_225 && deg < DEGREE_270 -> SOUTH_WEST
            deg >= DEGREE_270 && deg < DEGREE_315 -> WEST
            deg >= DEGREE_315 && deg < DEGREE_360 -> NORTH_WEST
            else -> NORTH
        }
    }

    private fun buildIconUrl(dto: WeatherInfoDTO): String? {
        return if (dto.weather.isNotEmpty())
            "$iconBaseUrl${dto.weather[0].icon}.png"
        else
            null
    }

    companion object {
        const val DEGREE_45 = 45f
        const val DEGREE_90 = 90f
        const val DEGREE_135 = 135f
        const val DEGREE_180 = 180f
        const val DEGREE_225 = 225f
        const val DEGREE_270 = 270f
        const val DEGREE_315 = 315f
        const val DEGREE_360 = 360f

        const val CONVERSION_FACTOR = 1000
    }
}