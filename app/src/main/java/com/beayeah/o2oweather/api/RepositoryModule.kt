/**
 * Dagger module for providing API services and repositories related to weather data.
 */
package com.beayeah.o2oweather.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger module that provides instances of WeatherApi and WeatherRepository.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    /**
     * Provides a singleton instance of WeatherApi.
     *
     * @return WeatherApi instance configured with base URL and Gson converter.
     */
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl("https://wttr.in/") // Base URL for the weather API
            .addConverterFactory(GsonConverterFactory.create()) // Gson converter for JSON parsing
            .build()
            .create(WeatherApi::class.java) // Create WeatherApi interface implementation
    }

    /**
     * Provides a singleton instance of WeatherRepository.
     *
     * @param api Instance of WeatherApi used by the repository.
     * @return WeatherRepository instance.
     */
    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi): WeatherRepository {
        return WeatherRepositoryImpl(api) // Initialize WeatherRepository implementation with WeatherApi instance
    }
}
