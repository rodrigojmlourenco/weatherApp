package io.procrastination.weather.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
class CachedWeatherInfo(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "conditions") val conditions: String,
    @ColumnInfo(name = "temperature") val temperature: Int,
    @ColumnInfo(name = "windSpeed") val windSpeed: Int,
    @ColumnInfo(name = "windDirection") val windDirecton: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "city") val city: String?,
    @ColumnInfo(name = "countryCode") val countryCode: String?,
    @ColumnInfo(name = "zipCode") val zipCode: String?,
    @ColumnInfo(name = "icon") val icon: String?
)