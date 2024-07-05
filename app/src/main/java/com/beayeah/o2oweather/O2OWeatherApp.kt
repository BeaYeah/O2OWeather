/**
 * Application class for O2OWeatherApp, initialized with Hilt for dependency injection.
 */
package com.beayeah.o2oweather

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Android Application class for O2OWeatherApp.
 *
 * Initializes the application with Hilt for dependency injection.
 */
@HiltAndroidApp
class O2OWeatherApp : Application() {
}
