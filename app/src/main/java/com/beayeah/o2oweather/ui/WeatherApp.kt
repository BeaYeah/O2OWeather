package com.beayeah.o2oweather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.beayeah.o2oweather.R
import com.beayeah.o2oweather.models.ForecastWeather
import com.beayeah.o2oweather.models.WeatherData
import com.beayeah.o2oweather.ui.theme.semiTransparentWhite
import com.beayeah.o2oweather.utils.Utils.getWeatherIcon
import com.beayeah.o2oweather.viewModels.WeatherState
import com.beayeah.o2oweather.viewModels.WeatherViewModel
import java.util.Locale

/**
 * Main composable function for the Weather App.
 *
 * @param viewModel The ViewModel for fetching and managing weather data.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(viewModel: WeatherViewModel = hiltViewModel()) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    // State for managing the city input by the user
    var city by remember { mutableStateOf("") }
    var isCityEmpty by remember { mutableStateOf(false) }

    // Background image and layout setup
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.background_weather_image),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            // Scaffold for top bar and main content
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = {
                            Text(stringResource(id = R.string.app_name))
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = semiTransparentWhite)
                    )
                },
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                            .padding(10.dp)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Input field for city name
                        BasicTextField(
                            value = city,
                            onValueChange = {
                                city = it
                                isCityEmpty = it.isEmpty()
                                // Clear weather state when city text changes
                                viewModel.clearWeather()
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(semiTransparentWhite, RoundedCornerShape(8.dp))
                                .padding(16.dp),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    if (city.isEmpty()) {
                                        Text(
                                            text = stringResource(id = R.string.city_name_hint),
                                            color = Color.LightGray
                                        )
                                    }
                                    innerTextField()
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        // Button to fetch weather data
                        Button(
                            onClick = {
                                val formattedCity = city.trim().replace(" ", "+")
                                viewModel.fetchWeather(formattedCity, context)
                            },
                            enabled = city.isNotEmpty(),
                            shape = RoundedCornerShape(10)
                        ) {
                            Text(stringResource(id = R.string.get_weather_btn))
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Observe changes in weatherData
                        val weatherState by viewModel.weatherData.collectAsState()

                        // Display content based on weatherState
                        when (weatherState) {
                            is WeatherState.Idle -> Text("")
                            is WeatherState.Loading -> CircularProgressIndicator()
                            is WeatherState.Success -> {
                                // Only show WeatherDisplay if city is not empty
                                if (city.isNotEmpty()) {
                                    WeatherDisplay(
                                        (weatherState as WeatherState.Success).weatherData,
                                        city
                                    )
                                }
                            }

                            is WeatherState.Error -> Text("Error: ${(weatherState as WeatherState.Error).message}")
                        }
                    }
                }
            )
        }
    }
}

/**
 * Displays weather information for the current city.
 *
 * @param weatherData The current weather data.
 * @param city The name of the city.
 */
@Composable
fun WeatherDisplay(weatherData: WeatherData, city: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.current_weather),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )

        WeatherCard(weatherData, city)

        Text(
            text = stringResource(id = R.string.forecast),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            weatherData.weather.forEach { forecast ->
                ForecastCard(forecast)
            }
        }
    }
}

/**
 * Displays a card with the current weather data.
 *
 * @param weatherData The current weather data.
 * @param city The name of the city.
 */
@Composable
fun WeatherCard(weatherData: WeatherData, city: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier.padding(10.dp),
        colors = CardDefaults.cardColors(containerColor = semiTransparentWhite)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image on the left side
            val weatherIcon = getWeatherIcon(weatherData.currentCondition[0].weatherCode)
            Image(
                painter = painterResource(id = weatherIcon),
                contentDescription = stringResource(id = R.string.weather_icon),
                modifier = Modifier.size(95.dp)
            )

            // Spacer to create gap between image and text
            Spacer(modifier = Modifier.width(16.dp))

            // Column for text content aligned to the left
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                // Text aligned to the right of the image
                Text(
                    text = city.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + " - " + weatherData.currentCondition[0].observationTime,
                    textAlign = TextAlign.Left, fontWeight = FontWeight.Bold
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = weatherData.currentCondition[0].weatherDescription[0].value,
                    fontWeight = FontWeight.Bold
                )
                Text(text = stringResource(id = R.string.temperature) + ": ${weatherData.currentCondition[0].tempC}°C")
                Text(text = stringResource(id = R.string.humidity) + ": ${weatherData.currentCondition[0].humidity}%")
                Text(text = stringResource(id = R.string.windSpeed) + ": ${weatherData.currentCondition[0].windSpeedKmph} km/h")
            }
        }
    }
}

/**
 * Displays a card with the forecast weather data.
 *
 * @param forecast The forecast weather data.
 */
@Composable
fun ForecastCard(forecast: ForecastWeather) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier.padding(5.dp),
        colors = CardDefaults.cardColors(containerColor = semiTransparentWhite)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = forecast.getFormattedDate(), fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(3.dp))
            Text(text = stringResource(id = R.string.minTemp) + ": ${forecast.minTempC}°C")
            Text(text = stringResource(id = R.string.maxTemp) + ": ${forecast.maxTempC}°C")
        }
    }
}
