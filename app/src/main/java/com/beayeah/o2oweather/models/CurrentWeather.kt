package com.beayeah.o2oweather.models

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("FeelsLikeC")
    val feelsLikeC: String,
    @SerializedName("FeelsLikeF")
    val feelsLikeF: String,
    @SerializedName("cloudcover")
    val cloudCover: String,
    @SerializedName("humidity")
    val humidity: String,
    @SerializedName("lang_es")
    val weatherDescription: List<WeatherDescription>,
    @SerializedName("localObsDateTime")
    val localObsDateTime: String,
    @SerializedName("observation_time")
    val observationTime: String,
    @SerializedName("precipInches")
    val precipInches: String,
    @SerializedName("precipMM")
    val precipMM: String,
    @SerializedName("pressure")
    val pressure: String,
    @SerializedName("pressureInches")
    val pressureInches: String,
    @SerializedName("temp_C")
    val tempC: String,
    @SerializedName("temp_F")
    val tempF: String,
    @SerializedName("uvIndex")
    val uvIndex: String,
    @SerializedName("visibility")
    val visibility: String,
    @SerializedName("visibilityMiles")
    val visibilityMiles: String,
    @SerializedName("weatherCode")
    val weatherCode: String,
    @SerializedName("winddir16Point")
    val windDir16Point: String,
    @SerializedName("winddirDegree")
    val windDirDegree: String,
    @SerializedName("windspeedKmph")
    val windSpeedKmph: String,
    @SerializedName("windspeedMiles")
    val windSpeedMiles: String
)