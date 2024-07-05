package com.beayeah.o2oweather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beayeah.o2oweather.api.WeatherRepository
import com.beayeah.o2oweather.models.WeatherData
import com.beayeah.o2oweather.viewModels.WeatherState
import com.beayeah.o2oweather.viewModels.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var weatherRepository: WeatherRepository

    private lateinit var viewModel: WeatherViewModel
    private val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        viewModel = WeatherViewModel(weatherRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchWeather sets Success state on success`() = dispatcher.runBlockingTest {
        val weatherData = WeatherData(/* mock data */)
        Mockito.`when`(weatherRepository.fetchWeather("London")).thenReturn(Result.success(weatherData))

        viewModel.fetchWeather("London")
        val state = viewModel.weatherData.first()
        assert(state is WeatherState.Success)
    }

    @Test
    fun `fetchWeather sets Error state on failure`() = dispatcher.runBlockingTest {
        Mockito.`when`(weatherRepository.fetchWeather("London")).thenReturn(Result.failure(Exception("Network Error")))

        viewModel.fetchWeather("London")
        val state = viewModel.weatherData.first()
        assert(state is WeatherState.Error)
    }
}