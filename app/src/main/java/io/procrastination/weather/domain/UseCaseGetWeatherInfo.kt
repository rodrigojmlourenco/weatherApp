package io.procrastination.weather.domain

import io.procrastination.foundation.domain.schedueler.Scheduler
import io.procrastination.foundation.domain.usecases.ObservableUseCase
import io.procrastination.weather.domain.error.CachedInformationIsTooOldException
import io.procrastination.weather.domain.model.LocationInfo
import io.procrastination.weather.domain.model.WeatherInfo
import io.procrastination.weather.domain.protocols.LocalWeatherRepository
import io.procrastination.weather.domain.protocols.NetworkHandler
import io.procrastination.weather.domain.protocols.WeatherRepository
import io.reactivex.Observable
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * As a user I want to know, given my current location ([LocationInfo]), what are the current
 * weather conditions in my area.
 *
 * The weather information should be cached for future offline use.
 * If I am offline, and there is weather information cached that is less than 24 hours old, then the
 * last known conditions and location should be shown along with a prominent display to indicate when
 * the data was last updated.
 *
 * If I am offline, and there are no previous conditions known, or the previous conditions are more
 * than 24 hours old, then I want to know the data is too old.
 */
class UseCaseGetWeatherInfo
constructor(scheduler: Scheduler,
            private val repository: WeatherRepository,
            private val networkHandler: NetworkHandler,
            private val localRepository: LocalWeatherRepository)

    : ObservableUseCase<WeatherInfo, LocationInfo>(scheduler){


    override fun buildUseCaseObservable(params: LocationInfo): Observable<WeatherInfo> {
        return if(networkHandler.hasNetworkConnectivity()) {
            loadFromNetwork(params)
        } else {
            loadFromCache(params)
        }
    }

    private fun tooOld(info : WeatherInfo) : Boolean {
        val expires = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)
        return info.lastUpdatedAt.time < expires
    }

    private fun loadFromNetwork(params : LocationInfo) : Observable<WeatherInfo>{
        return repository.getWeatherInfo(params.latitude, params.longitude)
                .map { weatherInfo ->
                    try {
                        localRepository.cache(weatherInfo).blockingGet()
                    }catch (e : Exception){
                        Timber.e(e)
                        throw e
                    }

                    weatherInfo
                }.toObservable()
    }

    private fun loadFromCache(params : LocationInfo) : Observable<WeatherInfo>{
        return when {
            params.city != null -> localRepository.getWeatherInfo(params.city)
            else -> localRepository.getWeatherInfo(params.latitude, params.longitude)
        }.map { weatherInfo ->

            if(tooOld(weatherInfo)) {
                localRepository.delete(weatherInfo)
                throw CachedInformationIsTooOldException()
            }else
                weatherInfo
        }.toObservable()
    }

}