package com.beayeah.o2oweather

import com.beayeah.o2oweather.api.WeatherApi
import com.beayeah.o2oweather.api.WeatherRepository
import com.beayeah.o2oweather.api.WeatherRepositoryImpl
import com.beayeah.o2oweather.models.WeatherData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.HttpException

class WeatherRepositoryTest {

    @Mock
    private lateinit var weatherApi: WeatherApi

    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        weatherRepository = WeatherRepositoryImpl(weatherApi)
    }

    @Test
    fun `fetchWeather returns success`() = runBlocking {
        val weatherData = WeatherData(/* mock data */)
        Mockito.`when`(weatherApi.getWeather("London")).thenReturn(weatherData)

        val result = weatherRepository.fetchWeather("London")
        assertTrue(result.isSuccess)
    }

    @Test
    fun `fetchWeather returns failure on HttpException`() = runBlocking {
        Mockito.`when`(weatherApi.getWeather("London")).thenThrow(HttpException::class.java)

        val result = weatherRepository.fetchWeather("London")
        assertTrue(result.isFailure)
    }
}