package com.beayeah.o2oweather.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class ForecastWeather(
    @SerializedName("date")
    val date: String,
    @SerializedName("maxtempC")
    val maxTempC: String,
    @SerializedName("mintempC")
    val minTempC: String,
) {
    fun getFormattedDate(): String {
        val localDate = LocalDate.parse(date)
        val formatter = DateTimeFormatter.ofPattern("dd/MM")
        return localDate.format(formatter)
    }
}