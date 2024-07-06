package com.beayeah.o2oweather.models

import com.google.gson.annotations.SerializedName

/**
 * Data class representing weather data fetched from API.
 *
 * This class encapsulates the current weather condition and weather forecast
 * obtained from JSON response using Gson serialization.
 *
 * @property currentCondition List of current weather conditions.
 * @property weather List of weather forecasts.
 */
data class WeatherData(
    @SerializedName("current_condition")
    val currentCondition: List<CurrentWeather>,
    @SerializedName("weather")
    val weather: List<ForecastWeather>
)
