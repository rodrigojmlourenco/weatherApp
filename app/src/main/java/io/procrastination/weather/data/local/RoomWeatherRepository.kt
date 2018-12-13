package io.procrastination.weather.data.local

import io.procrastination.weather.domain.error.NoInformationAvailableToPresentToTheUserException
import io.procrastination.weather.domain.model.WeatherInfo
import io.procrastination.weather.domain.protocols.LocalWeatherRepository
import io.reactivex.Single

class RoomWeatherRepository(database: WeatherDatabase) : LocalWeatherRepository {

    private val weatherDao: CachedWeatherDao = database.getWeatherDao()

    override fun cache(info: WeatherInfo): Single<Boolean> {
        return Single.fromCallable {
            weatherDao.insert(CachedWeatherMapper().fromModel(info))
            true
        }
    }

    override fun getWeatherInfo(city: String, zipCode: String?, country: String?): Single<WeatherInfo> {
        return weatherDao.getCachedWeatherFromCity(city)
            .map {
                if (it.isEmpty())
                    throw NoInformationAvailableToPresentToTheUserException()
                else
                    CachedWeatherMapper().toModel(it[0])
            }
    }

    override fun delete(info: WeatherInfo): Single<Boolean> {
        return Single.fromCallable {
            weatherDao.clear(CachedWeatherMapper().fromModel(info))
            true
        }
    }

    override fun clear(): Single<Boolean> {
        return Single.fromCallable {
            weatherDao.clear()
            true
        }
    }

    override fun getWeatherInfo(lat: Double, lng: Double): Single<WeatherInfo> {
        return weatherDao.getCachedWeatherGivenCoordinates(lat, lng)
            .map {
                if (it.isEmpty())
                    throw Exception()
                else
                    CachedWeatherMapper().toModel(it[0])
            }
    }
}