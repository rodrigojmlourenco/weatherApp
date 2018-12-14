package io.procrastination.weather.data.network

import io.procrastination.weather.domain.model.LocationInfo
import org.junit.Assert
import org.junit.Test
import java.util.Date

class NetworkWeatherRepoTest {

    private val apiKey = "b6907d289e10d714a6e88b30761fae22"
    private val baseUrl = "https://samples.openweathermap.org/data/2.5/"
    private val iconsUrl = "http://openweathermap.org/img/w/"

    @Test
    fun testRepo_fetchByCoordinates() {

        val repo = NetworkWeatherRepo(baseUrl, apiKey, iconsUrl)

        val testObs = repo.getWeatherInfo(33.toDouble(), 33.toDouble()).test()

        testObs.assertComplete()
        val value = testObs.values()[0]

        Assert.assertEquals("Clear", value.condition)
        Assert.assertEquals(285, value.temperature)
        Assert.assertEquals(5, value.windSpeed)
        Assert.assertEquals(6, value.windDirection)
        Assert.assertEquals(Date(1485792967L * 1000), value.lastUpdatedAt)
    }

    @Test
    fun testRepo_fetchByCity() {

        val repo = NetworkWeatherRepo(baseUrl, apiKey, iconsUrl)

        val testObs = repo.getWeatherInfo("London").test()

        testObs.assertComplete()
        val value = testObs.values()[0]

        Assert.assertEquals("Drizzle", value.condition)
        Assert.assertEquals(280, value.temperature)
        Assert.assertEquals(4, value.windSpeed)
        Assert.assertEquals(1, value.windDirection)
        Assert.assertEquals(Date(1485789600L * 1000), value.lastUpdatedAt)
        Assert.assertEquals(LocationInfo(51.51, -0.13, "London", "GB"), value.location)
    }

    @Test
    fun testRepo_fetchByZipCode() {
        val repo = NetworkWeatherRepo(baseUrl, apiKey, iconsUrl)

        val testObs = repo.getWeatherInfo("", zipCode = "9404").test()

        testObs.assertComplete()
        val value = testObs.values()[0]

        Assert.assertEquals("Rain", value.condition)
        Assert.assertEquals(280, value.temperature)
        Assert.assertEquals(8, value.windSpeed)
        Assert.assertEquals(7, value.windDirection)
        Assert.assertEquals(Date(1519061700L * 1000), value.lastUpdatedAt)
        Assert.assertEquals(LocationInfo(37.39, -122.09, "Mountain View", "US"), value.location)
    }
}