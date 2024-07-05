/**
 * Main activity for O2OWeatherApp, using Jetpack Compose and initialized with Hilt for dependency injection.
 */
package com.beayeah.o2oweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.beayeah.o2oweather.ui.WeatherApp
import dagger.hilt.android.AndroidEntryPoint

/**
 * Android entry point activity for O2OWeatherApp.
 *
 * Initializes the activity with Hilt for dependency injection and sets content using Jetpack Compose.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    /**
     * Called when the activity is starting.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *     this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *     Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp() // Set content of the activity using WeatherApp composable
        }
    }
}
