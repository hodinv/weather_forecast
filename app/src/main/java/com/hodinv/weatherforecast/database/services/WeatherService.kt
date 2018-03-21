package com.hodinv.weatherforecast.database.services

import android.util.Log
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.dao.WeatherDao

/**
 * Created by vasily on 21.03.18.
 */
class WeatherService(val weatherDao: WeatherDao, val placesService: PlacesService, val notifyAboutChanges: () -> Unit) {
    fun putWeather(info: WeatherInfo) {
        val item = weatherDao.getById(info.id);
        if (item == null) {
            weatherDao.insertNew(info)
        } else {
            weatherDao.update(info)
        }
        placesService.setCityUpdateTimeToCurrent(info.id)
        notifyAboutChanges()
    }

    fun getWeatherInfo(): List<WeatherInfo> {
        return weatherDao.getAll()
    }
}