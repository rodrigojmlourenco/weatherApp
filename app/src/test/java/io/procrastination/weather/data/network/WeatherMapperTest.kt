package io.procrastination.weather.data.network

import com.google.gson.Gson
import io.procrastination.weather.domain.model.LocationInfo
import io.procrastination.weather.domain.model.WeatherInfo
import org.junit.Assert.*
import org.junit.Test
import java.util.*

class WeatherMapperTest {

    @Test
    fun testConvertion() {
        val gson = Gson()
        val sampleAsJson : WeatherInfoDTO = gson.fromJson(sample, WeatherInfoDTO::class.java)

        val testSubject = WeatherMapper().toModel(sampleAsJson)
        val groundTruth = WeatherInfo("Clear", 285, 5, 6, Date(1485792967L * 1000), LocationInfo(35.02,139.01, "Tawarano", "JP") )

        assertEquals(groundTruth, testSubject)
    }

    val sample = "{\"coord\":{\"lon\":139.01,\"lat\":35.02},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01n\"}],\"base\":\"stations\",\"main\":{\"temp\":285.514,\"pressure\":1013.75,\"humidity\":100,\"temp_min\":285.514,\"temp_max\":285.514,\"sea_level\":1023.22,\"grnd_level\":1013.75},\"wind\":{\"speed\":5.52,\"deg\":311},\"clouds\":{\"all\":0},\"dt\":1485792967,\"sys\":{\"message\":0.0025,\"country\":\"JP\",\"sunrise\":1485726240,\"sunset\":1485763863},\"id\":1907296,\"name\":\"Tawarano\",\"cod\":200}"
}