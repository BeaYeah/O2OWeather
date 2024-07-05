/**
 * Interface defining operations for fetching weather data.
 */
package com.beayeah.o2oweather.api

import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interface for fetching weather data for a specific city.
 */
interface WeatherRepository {

    /**
     * Fetches weather data for the specified city.
     *
     * @param city Name of the city for which weather data is requested.
     * @return Result<String> indicating success or failure of the operation.
     */
    suspend fun fetchWeather(city: String): Result<String>
}

/**
 * Implementation of WeatherRepository interface that interacts with WeatherApi to fetch weather data.
 *
 * @param weatherApi Instance of WeatherApi used for fetching weather data.
 */
@Singleton
class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    /**
     * Fetches weather data for the specified city.
     *
     * @param city Name of the city for which weather data is requested.
     * @return Result<String> indicating success or failure of the operation.
     */
    override suspend fun fetchWeather(city: String): Result<String> {
        return try {
            val response = weatherApi.getWeather(city)
            Result.success(response.toString()) // Convert response to String and wrap in Result.success
        } catch (e: HttpException) {
            Result.failure(e) // Handle HTTP exceptions and wrap in Result.failure
        } catch (e: Exception) {
            Result.failure(e) // Handle other exceptions and wrap in Result.failure
        }
    }
}
