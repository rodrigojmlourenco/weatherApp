package io.procrastination.weather.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface CachedWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: CachedWeatherInfo)

    @Query("SELECT * FROM weather WHERE city == :city")
    fun getCachedWeatherFromCity(city: String): Single<List<CachedWeatherInfo>>

    @Query("SELECT * FROM weather WHERE latitude == :lat AND longitude == :lng")
    fun getCachedWeatherGivenCoordinates(lat: Double, lng: Double): Single<List<CachedWeatherInfo>>

    @Query("SELECT * FROM weather")
    fun getAll(): Single<List<CachedWeatherInfo>>

    @Query("DELETE FROM weather")
    fun clear()

    @Delete
    fun clear(item: CachedWeatherInfo)
}