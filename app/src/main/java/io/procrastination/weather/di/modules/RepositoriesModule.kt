package io.procrastination.weather.di.modules

import dagger.Module
import dagger.Provides
import io.procrastination.weather.data.network.NetworkWeatherRepo
import io.procrastination.weather.domain.protocols.WeatherRepository
import javax.inject.Named

@Module
class RepositoriesModule {

    @Provides
    fun provideWeatherRepository(@Named("weather-endpoint") baseUrl : String,
                                 @Named("weather-key") apiKey : String) : WeatherRepository {

        return NetworkWeatherRepo(baseUrl, apiKey)
    }
}