package com.beayeah.o2oweather.models

import com.google.gson.annotations.SerializedName

data class ForecastWeather(
    @SerializedName("avgtempC")
    val avgTempC: String,
    @SerializedName("avgtempF")
    val avgTempF: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("maxtempC")
    val maxTempC: String,
    @SerializedName("maxtempF")
    val maxTempF: String,
    @SerializedName("mintempC")
    val minTempC: String,
    @SerializedName("mintempF")
    val minTempF: String
)