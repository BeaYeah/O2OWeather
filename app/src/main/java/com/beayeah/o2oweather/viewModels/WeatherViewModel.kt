package com.beayeah.o2oweather.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beayeah.o2oweather.api.WeatherRepository
import com.beayeah.o2oweather.models.WeatherData
import com.beayeah.o2oweather.utils.NetworkUtils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import retrofit2.HttpException

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val context: Application
) : ViewModel() {

    private val _weatherData = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val weatherData: StateFlow<WeatherState> = _weatherData

    fun fetchWeather(city: String) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            _weatherData.value = WeatherState.Error("No internet connection")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _weatherData.value = WeatherState.Loading
            val result = weatherRepository.fetchWeather(city)
            _weatherData.value = if (result.isSuccess) {
                val weatherData = parseWeatherResponse(result.getOrNull()!!)
                WeatherState.Success(weatherData)
            } else {
                handleWeatherError(result.exceptionOrNull())
                WeatherState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }

    fun clearWeather() {
        _weatherData.value = WeatherState.Idle
    }

    private fun handleWeatherError(exception: Throwable?): WeatherState {
        return when (exception) {
            is HttpException -> {
                if (exception.code() == 404) {
                    WeatherState.Error("City not found")
                } else {
                    WeatherState.Error("HTTP Error ${exception.code()}")
                }
            }
            else -> WeatherState.Error(exception?.message ?: "Unknown error")
        }
    }
}

fun parseWeatherResponse(json: String): WeatherData {
    val gson = Gson()
    val weatherData = gson.fromJson(json, WeatherData::class.java)
    return weatherData
}

sealed class WeatherState {
    data object Idle : WeatherState()
    data object Loading : WeatherState()
    data class Success(val weatherData: WeatherData) : WeatherState()
    data class Error(val message: String) : WeatherState()
}