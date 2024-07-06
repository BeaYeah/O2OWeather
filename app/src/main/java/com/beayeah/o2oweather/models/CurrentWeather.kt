package com.beayeah.o2oweather.models

import com.google.gson.annotations.SerializedName

/**
 * Data class representing current weather information.
 *
 * This class encapsulates current weather data including humidity, weather description,
 * observation time, temperature in Celsius, weather code, and wind speed obtained from
 * JSON response using Gson serialization.
 *
 * @property humidity The humidity percentage.
 * @property weatherDescription List of weather descriptions in Spanish.
 * @property observationTime The time of observation.
 * @property tempC The temperature in Celsius.
 * @property weatherCode The weather code.
 * @property windSpeedKmph The wind speed in kilometers per hour.
 */
data class CurrentWeather(
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("lang_es")
    val weatherDescription: List<WeatherDescription>,
    @SerializedName("observation_time")
    val observationTime: String,
    @SerializedName("temp_C")
    val tempC: String,
    @SerializedName("weatherCode")
    val weatherCode: String,
    @SerializedName("windspeedKmph")
    val windSpeedKmph: String,
)
