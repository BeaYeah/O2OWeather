package com.beayeah.o2oweather.models

import com.google.gson.annotations.SerializedName

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