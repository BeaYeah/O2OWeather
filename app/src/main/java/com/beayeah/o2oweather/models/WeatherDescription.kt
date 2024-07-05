package com.beayeah.o2oweather.models

import com.google.gson.annotations.SerializedName

data class WeatherDescription(
    @SerializedName("value")
    val value: String
)