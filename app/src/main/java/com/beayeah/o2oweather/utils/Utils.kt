package com.beayeah.o2oweather.utils

import com.beayeah.o2oweather.R

/**
 * Utility object for retrieving weather icon resources based on weather descriptions.
 *
 * Provides a method to determine the appropriate drawable resource ID for a given weather description.
 */
object Utils {

    /**
     * Retrieves the appropriate weather icon drawable resource ID based on the weather description.
     *
     * @param description The weather description used to determine the icon resource.
     * @return The drawable resource ID corresponding to the weather icon.
     */
    fun getWeatherIcon(description: String): Int {
        return when {
            "sunny" in WeatherCodes.getDescription(description) -> R.drawable.sun
            "cloudy" in WeatherCodes.getDescription(description) -> R.drawable.cloudy
            "fog" in WeatherCodes.getDescription(description) -> R.drawable.fog
            "showers" in WeatherCodes.getDescription(description) -> R.drawable.rain
            "thundery" in WeatherCodes.getDescription(description) -> R.drawable.thunder
            "snow" in WeatherCodes.getDescription(description) -> R.drawable.snow
            "rain" in WeatherCodes.getDescription(description) -> R.drawable.rain
            else -> R.drawable.sun // Default to sunny icon if no matching description is found
        }
    }
}
