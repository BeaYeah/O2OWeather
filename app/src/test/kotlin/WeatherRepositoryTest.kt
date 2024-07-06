import com.beayeah.o2oweather.api.WeatherApi
import com.beayeah.o2oweather.api.WeatherRepository
import com.beayeah.o2oweather.api.WeatherRepositoryImpl
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.HttpException

/**
 * Unit tests for [WeatherRepositoryImpl] using [WeatherApi] mocks.
 */
class WeatherRepositoryTest {

    private lateinit var weatherApi: WeatherApi
    private lateinit var weatherRepository: WeatherRepository

    /**
     * Sets up dependencies for each test.
     */
    @Before
    fun setUp() {
        weatherApi = mock(WeatherApi::class.java)
        weatherRepository = WeatherRepositoryImpl(weatherApi)
    }

    /**
     * Tests [WeatherRepository.fetchWeather] for a successful API call scenario.
     */
    @Test
    fun `fetchWeather returns success when api call is successful`() = runTest {
        // Given
        val city = "London"
        val jsonString = """
            {
                "current_condition":[
                    {
                        "humidity":"43",
                        "lang_es":[
                            {
                                "value":"Soleado"
                            }
                        ],
                        "observation_time":"03:44 PM",
                        "temp_C":"31",
                        "weatherCode":"113",
                        "weatherDesc":[
                            {
                                "value":"Sunny"
                            }
                        ],
                        "windspeedKmph":"28"
                    }
                ],
                "weather":[
                    {
                        "avgtempC":"29",
                        "date":"2024-07-05",
                        "maxtempC":"38",
                        "mintempC":"21"
                    },
                    {
                        "avgtempC":"26",
                        "date":"2024-07-06",
                        "maxtempC":"35",
                        "mintempC":"21"
                    },
                    {
                        "avgtempC":"23",
                        "date":"2024-07-07",
                        "maxtempC":"33",
                        "mintempC":"16"
                    }
                ]
            }
        """
        val expectedJsonObject: JsonObject = Gson().fromJson(jsonString, JsonObject::class.java)

        `when`(weatherApi.getWeather(city)).thenReturn(expectedJsonObject)

        // When
        val result = weatherRepository.fetchWeather(city)

        // Then
        assertTrue(result.isSuccess)

        val actualJsonObject: JsonObject =
            Gson().fromJson(result.getOrNull(), JsonObject::class.java)

        assertEquals(expectedJsonObject, actualJsonObject)
    }

    /**
     * Tests [WeatherRepository.fetchWeather] for a failure scenario when API call throws [HttpException].
     */
    @Test
    fun `fetchWeather returns failure when api call throws HttpException`() = runTest {
        // Given
        val city = "InvalidCity"
        val httpException = mock(HttpException::class.java)
        `when`(weatherApi.getWeather(city)).thenThrow(httpException)

        // When
        val result = weatherRepository.fetchWeather(city)

        // Then
        assertTrue(result.isFailure)
        assertEquals(httpException, result.exceptionOrNull())
    }

    /**
     * Tests [WeatherRepository.fetchWeather] for a failure scenario when API call throws [RuntimeException].
     */
    @Test
    fun `fetchWeather returns failure when api call throws RuntimeException`() = runTest {
        // Given
        val city = "AnotherInvalidCity"
        val runtimeException = RuntimeException("Network error")
        `when`(weatherApi.getWeather(city)).thenThrow(runtimeException)

        // When
        val result = weatherRepository.fetchWeather(city)

        // Then
        assertTrue(result.isFailure)
        assertEquals(runtimeException, result.exceptionOrNull())
    }
}
