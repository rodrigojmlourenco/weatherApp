package io.procrastination.weather.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [CachedWeatherInfo::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase(){

    abstract fun getWeatherDao() : CachedWeatherDao

}