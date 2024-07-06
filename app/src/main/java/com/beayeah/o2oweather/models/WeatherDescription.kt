package com.beayeah.o2oweather.models

import com.google.gson.annotations.SerializedName

/**
 * Data class representing weather description fetched from API.
 *
 * This class encapsulates the weather description value obtained from
 * JSON response using Gson serialization.
 *
 * @property value The weather description string.
 */
data class WeatherDescription(
    @SerializedName("value")
    val value: String
)
