package com.beayeah.o2oweather.utils

/**
 * Utility object for mapping weather codes to descriptive strings.
 *
 * Provides a method to retrieve weather descriptions based on specific weather codes.
 */
object WeatherCodes {

    // Map of weather codes to corresponding weather descriptions
    private val WWO_CODE = mapOf(
        "113" to "Sunny",
        "116" to "PartlyCloudy",
        "119" to "Cloudy",
        "122" to "VeryCloudy",
        "143" to "Fog",
        "176" to "LightShowers",
        "179" to "LightSleetShowers",
        "182" to "LightSleet",
        "185" to "LightSleet",
        "200" to "ThunderyShowers",
        "227" to "LightSnow",
        "230" to "HeavySnow",
        "248" to "Fog",
        "260" to "Fog",
        "263" to "LightShowers",
        "266" to "LightRain",
        "281" to "LightSleet",
        "284" to "LightSleet",
        "293" to "LightRain",
        "296" to "LightRain",
        "299" to "HeavyShowers",
        "302" to "HeavyRain",
        "305" to "HeavyShowers",
        "308" to "HeavyRain",
        "311" to "LightSleet",
        "314" to "LightSleet",
        "317" to "LightSleet",
        "320" to "LightSnow",
        "323" to "LightSnowShowers",
        "326" to "LightSnowShowers",
        "329" to "HeavySnow",
        "332" to "HeavySnow",
        "335" to "HeavySnowShowers",
        "338" to "HeavySnow",
        "350" to "LightSleet",
        "353" to "LightShowers",
        "356" to "HeavyShowers",
        "359" to "HeavyRain",
        "362" to "LightSleetShowers",
        "365" to "LightSleetShowers",
        "368" to "LightSnowShowers",
        "371" to "HeavySnowShowers",
        "374" to "LightSleetShowers",
        "377" to "LightSleet",
        "386" to "ThunderyShowers",
        "389" to "ThunderyHeavyRain",
        "392" to "ThunderySnowShowers",
        "395" to "HeavySnowShowers"
    )

    /**
     * Retrieves the weather description for a given weather code.
     *
     * @param code The weather code to retrieve the description for.
     * @return The corresponding weather description or "Unknown Weather Code" if the code is not found.
     */
    fun getDescription(code: String): String {
        return WWO_CODE[code]?.lowercase() ?: "Unknown Weather Code"
    }
}
