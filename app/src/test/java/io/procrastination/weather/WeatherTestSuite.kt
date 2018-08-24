package io.procrastination.weather

import io.procrastination.weather.data.network.NetworkTestSuite
import io.procrastination.weather.domain.UseCaseGetWeatherInfoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(NetworkTestSuite::class,
        UseCaseGetWeatherInfoTest::class)
class WeatherTestSuite