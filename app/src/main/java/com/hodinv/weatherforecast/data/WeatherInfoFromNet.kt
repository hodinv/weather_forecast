package com.hodinv.weatherforecast.data


/**
 * Created by vasily on 18.03.18.
 */
class WeatherInfoFromNet : WeatherInfo() {

    var weather: Array<Weather> = emptyArray()

    fun getDbWeatherInfo(): WeatherInfo {
        val info = WeatherInfo()
        info.id = id
        info.main = main
        info.wind = wind
        info.name = name
        info.weatherInfo = weather.joinToString(separator = ", ", transform = { it.description })
        return info
    }
}