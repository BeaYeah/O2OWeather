package com.beayeah.o2oweather.models

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("current_condition")
    val currentCondition: List<CurrentWeather>,
    @SerializedName("weather")
    val weather: List<ForecastWeather>
)
