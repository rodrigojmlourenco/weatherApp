package io.procrastination.weather.domain

import io.procrastination.foundation.domain.schedueler.Scheduler
import io.procrastination.foundation.domain.usecases.ObservableUseCase
import io.procrastination.weather.domain.model.LocationInfo
import io.procrastination.weather.domain.model.WeatherInfo
import io.procrastination.weather.domain.protocols.WeatherRepository
import io.reactivex.Observable

/**
 * As a user I want to know, given my current location ([LocationInfo]), what are the current
 * weather conditions in my area.
 */
class UseCaseGetWeatherInfo
constructor(scheduler: Scheduler, private val repository: WeatherRepository)
    : ObservableUseCase<WeatherInfo, LocationInfo>(scheduler){

    override fun buildUseCaseObservable(params: LocationInfo): Observable<WeatherInfo> {
        return repository.getWeatherInfo(params.latitude, params.longitude).toObservable()
    }

}