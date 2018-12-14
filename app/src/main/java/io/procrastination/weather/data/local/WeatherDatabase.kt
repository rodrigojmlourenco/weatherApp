package io.procrastination.weather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CachedWeatherInfo::class], version = 3, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getWeatherDao(): CachedWeatherDao
}