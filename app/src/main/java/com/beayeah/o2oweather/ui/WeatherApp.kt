package com.beayeah.o2oweather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherApp(viewModel: WeatherViewModel = hiltViewModel()) {
    var city by remember { mutableStateOf("") }
    var isCityEmpty by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

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
                            .padding(16.dp)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        BasicTextField(
                            value = city,
                            onValueChange = {
                                city = it
                                isCityEmpty = it.isEmpty()
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
                        Button(
                            onClick = {
                                val formattedCity = city.trim().replace(" ", "+")
                                viewModel.fetchWeather(formattedCity)
                            },
                            enabled = city.isNotEmpty(),
                            shape = RoundedCornerShape(10)
                        ) {
                            Text(stringResource(id = R.string.get_weather_btn))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        val weatherState by viewModel.weatherData.collectAsState()

                        when (weatherState) {
                            is WeatherState.Idle -> Text("")
                            is WeatherState.Loading -> CircularProgressIndicator()
                            is WeatherState.Success -> WeatherDisplay((weatherState as WeatherState.Success).weatherData)
                            is WeatherState.Error -> Text("Error: ${(weatherState as WeatherState.Error).message}")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun WeatherDisplay(weatherData: WeatherData) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = stringResource(id = R.string.current_weather),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )
        WeatherCard(weatherData)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.forecast),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(8.dp)
        )

        weatherData.weather.forEach { forecast ->
            ForecastCard(forecast)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun WeatherCard(weatherData: WeatherData) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = semiTransparentWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.date) + ": ${weatherData.currentCondition[0].observationTime}")
            val weatherIcon = getWeatherIcon(weatherData.currentCondition[0].weatherCode)
            Image(
                painter = painterResource(id = weatherIcon),
                contentDescription = stringResource(id = R.string.weather_icon),
                modifier = Modifier.size(64.dp)
            )
            Text(text = stringResource(id = R.string.description) + ": ${weatherData.currentCondition[0].weatherDescription[0]}")
            Text(text = stringResource(id = R.string.temperature) + ": ${weatherData.currentCondition[0].tempC}°C")
            Text(text = stringResource(id = R.string.humidity) + ": ${weatherData.currentCondition[0].humidity}%")
            Text(text = stringResource(id = R.string.windSpeed) + ": ${weatherData.currentCondition[0].windSpeedKmph} km/h")
        }
    }
}

@Composable
fun ForecastCard(forecast: ForecastWeather) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(),
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = semiTransparentWhite)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.date) + ": ${forecast.date}")
            Text(text = stringResource(id = R.string.minTemp) + "Min Temp: ${forecast.minTempC}°C")
            Text(text = stringResource(id = R.string.maxTemp) + "Max Temp: ${forecast.maxTempC}°C")
        }
    }
}
