package com.hodinv.weatherforecast.database.repository

import com.hodinv.weatherforecast.data.WeatherInfo

/**
 * Created by vasily on 24.03.18.
 * Interface for weather repository
 */
interface WeatherRepository {
    /**
     * Adds weather info
     * @param info weather info, city is is inside record
     */
    fun putWeather(info: WeatherInfo)


    /**
     * Return list of weather info
     * @return list of recent weather info
     */
    fun getWeatherInfo(): List<WeatherInfo>

    /**
     * Return weather info for certain city
     * @param cityId id of city
     * @return weather info record
     */
    fun getWeatherInfo(cityId: Int): WeatherInfo
}