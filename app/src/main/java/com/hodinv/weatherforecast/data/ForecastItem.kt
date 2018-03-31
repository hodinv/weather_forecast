package com.hodinv.weatherforecast.data

/**
 * Created by vasily on 26.03.18.
 */
class ForecastItem {
    var dt: Long = 0L
    var main: MainInfo = MainInfo()
    var weather: Array<Weather> = emptyArray()
    var wind: Wind = Wind()

    fun getWeatherInfo(): String {
        return weather.joinToString(separator = ", ", transform = { it.description })
    }
}