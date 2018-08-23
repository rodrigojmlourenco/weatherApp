package io.procrastination.weather.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import io.procrastination.sample.R
import javax.inject.Named

@Module
class ConstantsModule {

    @Provides @Named("weather-endpoint")
    fun provideWeatherEndpoint() : String {
        return "https://samples.openweathermap.org/data/2.5/"
    }

    @Provides @Named("weather-key")
    fun provideWeatherApiKey(context : Context) : String {
        return context.getString(R.string.weather_api)
    }
}