package com.beayeah.o2oweather.utils

import com.beayeah.o2oweather.R

object Utils {
    fun getWeatherIcon(description: String): Int {
        return when {
            "sunny" in WeatherCodes.getDescription(description) -> R.drawable.sun
            "cloudy" in WeatherCodes.getDescription(description) -> R.drawable.cloudy
            "fog" in WeatherCodes.getDescription(description) -> R.drawable.fog
            "showers" in WeatherCodes.getDescription(description) -> R.drawable.rain
            "thundery" in WeatherCodes.getDescription(description) -> R.drawable.thunder
            "snow" in WeatherCodes.getDescription(description) -> R.drawable.snow
            "rain" in WeatherCodes.getDescription(description) -> R.drawable.rain
            else -> R.drawable.sun
        }
    }
}