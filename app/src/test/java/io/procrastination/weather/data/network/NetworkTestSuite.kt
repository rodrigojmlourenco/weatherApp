package io.procrastination.weather.data.network

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(WeatherMapperTest::class,
        NetworkWeatherRepoTest::class)
class NetworkTestSuite