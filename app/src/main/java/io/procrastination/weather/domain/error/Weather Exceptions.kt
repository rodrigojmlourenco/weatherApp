package io.procrastination.weather.domain.error

abstract class WeatherException : Exception()

class UnableToFetchWeatherInfo : WeatherException()

class LocationRequestNotPermitedException : WeatherException()