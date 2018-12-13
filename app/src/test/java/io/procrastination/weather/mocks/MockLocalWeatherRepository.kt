package io.procrastination.weather.mocks

import io.procrastination.weather.data.local.CachedWeatherInfo
import io.procrastination.weather.data.local.CachedWeatherMapper
import io.procrastination.weather.domain.error.NoInformationAvailableToPresentToTheUserException
import io.procrastination.weather.domain.model.WeatherInfo
import io.procrastination.weather.domain.protocols.LocalWeatherRepository
import io.reactivex.Single

class MockLocalWeatherRepository : LocalWeatherRepository {

    private val cache: MutableList<CachedWeatherInfo> = mutableListOf()
    private val mapper: CachedWeatherMapper = CachedWeatherMapper()

    fun syncInsert(info: WeatherInfo) {
        val item = mapper.fromModel(info)

        if (cache.any { it.id == item.id }) {
            cache.removeIf { it.id == item.id }
        }

        cache.add(item)
    }

    override fun cache(info: WeatherInfo): Single<Boolean> {

        val item = mapper.fromModel(info)

        return Single.fromCallable {

            if (cache.any { it.id == item.id }) {
                cache.removeIf { it.id == item.id }
            }

            cache.add(item)

            true
        }
    }

    override fun getWeatherInfo(lat: Double, lng: Double): Single<WeatherInfo> {

        return Single.fromCallable {

            val items = cache.filter { it.latitude == lat && it.longitude == lng }

            if (items.isEmpty())
                throw NoInformationAvailableToPresentToTheUserException()
            else
                items[0]
        }.map { mapper.toModel(it) }
    }

    override fun getWeatherInfo(city: String, zipCode: String?, country: String?): Single<WeatherInfo> {

        return Single.fromCallable {

            val items = cache.filter { it.city == city }

            if (items.isEmpty())
                throw NoInformationAvailableToPresentToTheUserException()
            else
                items[0]
        }.map { mapper.toModel(it) }
    }

    override fun delete(info: WeatherInfo): Single<Boolean> {
        return Single.fromCallable {
            cache.removeIf { it.city == info.location.city }
            true
        }
    }

    override fun clear(): Single<Boolean> {
        return Single.fromCallable {
            cache.clear()
            true
        }
    }
}