package com.beayeah.o2oweather.utils

import com.beayeah.o2oweather.R

object Utils {
    fun getWeatherIcon(description: String): Int {
        return when {
            "sunny" in description.lowercase() -> R.drawable.sun
            "cloudy" in description.lowercase() -> R.drawable.cloudy
            "fog" in description.lowercase() -> R.drawable.fog
            "showers" in description.lowercase() -> R.drawable.rain
            "thundery" in description.lowercase() -> R.drawable.thunder
            "snow" in description.lowercase() -> R.drawable.snow
            "rain" in description.lowercase() -> R.drawable.rain
            else -> R.drawable.sun
        }
    }
}