/**
 * Retrofit API interface for fetching weather data.
 */
package com.beayeah.o2oweather.api

import com.google.gson.JsonObject
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit API interface for fetching weather data from wttr.in.
 */
interface WeatherApi {

    /**
     * Fetches weather data for a specific city.
     *
     * @param city Name of the city for which weather data is requested.
     * @return JsonObject containing weather information in JSON format.
     */
    @GET("{city}?format=j1&lang=es")
    suspend fun getWeather(@Path("city") city: String): JsonObject
}
