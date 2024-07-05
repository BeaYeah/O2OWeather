/**
 * Dagger module for providing Retrofit instance.
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
 * Dagger module that provides Retrofit instance configured with GsonConverterFactory
 * for JSON parsing.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    /**
     * Provides a singleton instance of Retrofit.
     *
     * @return Retrofit instance configured with base URL and Gson converter.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://wttr.in/") // Base URL for the API service
            .addConverterFactory(GsonConverterFactory.create()) // Gson converter for JSON parsing
            .build()
    }
}
