package com.beayeah.o2oweather.models

import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Data class representing forecasted weather information.
 *
 * This class encapsulates forecasted weather data including date, maximum temperature,
 * and minimum temperature obtained from JSON response using Gson serialization.
 *
 * @property date The date of the forecast.
 * @property maxTempC The maximum temperature in Celsius for the forecasted day.
 * @property minTempC The minimum temperature in Celsius for the forecasted day.
 */
data class ForecastWeather(
    @SerializedName("date")
    val date: String,
    @SerializedName("maxtempC")
    val maxTempC: String,
    @SerializedName("mintempC")
    val minTempC: String,
) {
    /**
     * Formats the date from "yyyy-MM-dd" to "dd/MM".
     *
     * @return The formatted date string in "dd/MM" format.
     */
    fun getFormattedDate(): String {
        val localDate = LocalDate.parse(date)
        val formatter = DateTimeFormatter.ofPattern("dd/MM")
        return localDate.format(formatter)
    }
}
