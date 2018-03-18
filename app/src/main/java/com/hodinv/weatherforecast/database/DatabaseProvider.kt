package com.hodinv.weatherforecast.database

import com.hodinv.weatherforecast.data.WeatherInfo

/**
 * Created by vasily on 18.03.18.
 */
class DatabaseProvider {
    fun getCitiesList(): List<Int> {
        return listOf(588409, 2643743, 625144)
    }

    fun getCityLastUpdateTime(cityId: Int): Long {
        return 0L;
    }

    fun setCityLastUpdateTime(cityId: Int, time: Long) {

    }

    fun addCity(cityId: Int) {

    }

    fun removeCity(cityId: Int) {

    }

    fun putWeather(cityId: Int, info: WeatherInfo, time: Long) {

    }
}