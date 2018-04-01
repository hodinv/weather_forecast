package com.hodinv.weatherforecast.database.services

import com.hodinv.weatherforecast.data.WeatherInfo

/**
 * Created by vasily on 24.03.18.
 */
interface WeatherService {
    fun putWeather(info: WeatherInfo)
    fun getWeatherInfo(): List<WeatherInfo>
    fun getWeatherInfo(cityId: Int): WeatherInfo
}