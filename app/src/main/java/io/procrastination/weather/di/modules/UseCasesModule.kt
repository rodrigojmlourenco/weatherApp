package io.procrastination.weather.di.modules

import dagger.Module
import dagger.Provides
import io.procrastination.foundation.domain.schedueler.Scheduler
import io.procrastination.weather.domain.UseCaseGetWeatherInfo
import io.procrastination.weather.domain.protocols.WeatherRepository

@Module
class UseCasesModule {

    @Provides
    fun provideGetWeatherInfoUseCase(scheduler: Scheduler,
                                     repository: WeatherRepository) : UseCaseGetWeatherInfo{

        return UseCaseGetWeatherInfo(scheduler, repository)
    }
}