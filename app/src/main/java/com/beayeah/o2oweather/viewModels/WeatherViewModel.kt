package com.beayeah.o2oweather.viewModels

import android.app.Application
import android.content.Context
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.beayeah.o2oweather.R
import com.beayeah.o2oweather.api.WeatherRepository
import com.beayeah.o2oweather.models.WeatherData
import com.beayeah.o2oweather.utils.NetworkUtils
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

/**
 * View model responsible for managing weather data retrieval and state for the UI.
 *
 * @property weatherRepository The repository responsible for fetching weather data.
 * @property context The application context used to check network availability.
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val context: Application
) : ViewModel() {

    private val _weatherData = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val weatherData: StateFlow<WeatherState> = _weatherData

    /**
     * Initiates a weather data fetch for the provided city.
     *
     * Checks network availability before initiating the fetch. Updates [_weatherData] state flow
     * based on the result of the fetch operation.
     *
     * @param city The city for which weather data is requested.
     */
    fun fetchWeather(city: String, context: Context) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            _weatherData.value = WeatherState.Error(context.getString(R.string.no_internet_connection))
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            _weatherData.value = WeatherState.Loading
            val result = weatherRepository.fetchWeather(city)
            _weatherData.value = if (result.isSuccess) {
                val weatherData = parseWeatherResponse(result.getOrNull()!!)
                WeatherState.Success(weatherData)
            } else {
                handleWeatherError(result.exceptionOrNull(), context)
            }
        }
    }

    /**
     * Clears the weather data state.
     *
     * Sets [_weatherData] state flow to [WeatherState.Idle], indicating no weather data is currently available.
     */
    fun clearWeather() {
        _weatherData.value = WeatherState.Idle
    }

    /**
     * Handles specific error scenarios related to weather data fetching.
     *
     * @param exception The exception thrown during the weather data fetch operation.
     * @return The appropriate [WeatherState] based on the type of exception encountered.
     */
    private fun handleWeatherError(exception: Throwable?, context: Context): WeatherState {
        return when (exception) {
            is HttpException -> {
                if (exception.code() == 404) {
                    WeatherState.Error(context.getString(R.string.city_not_found))
                } else {
                    WeatherState.Error("HTTP Error ${exception.code()}")
                }
            }
            else -> WeatherState.Error(exception?.message ?: "Unknown error")
        }
    }
}

/**
 * Parses the JSON weather response into a [WeatherData] object.
 *
 * @param json The JSON string containing weather data.
 * @return The parsed [WeatherData] object.
 */
fun parseWeatherResponse(json: String): WeatherData {
    val gson = Gson()
    val weatherData = gson.fromJson(json, WeatherData::class.java)
    return weatherData
}

/**
 * Represents the different states of weather data retrieval.
 */
sealed class WeatherState {
    /**
     * Represents the idle state where no weather data retrieval operation is ongoing.
     */
    data object Idle : WeatherState()

    /**
     * Represents the state where weather data retrieval is in progress.
     */
    data object Loading : WeatherState()

    /**
     * Represents the successful retrieval of weather data.
     *
     * @property weatherData The weather data retrieved successfully.
     */
    data class Success(val weatherData: WeatherData) : WeatherState()

    /**
     * Represents an error encountered during weather data retrieval.
     *
     * @property message The error message describing the encountered error.
     */
    data class Error(val message: String) : WeatherState()
}
