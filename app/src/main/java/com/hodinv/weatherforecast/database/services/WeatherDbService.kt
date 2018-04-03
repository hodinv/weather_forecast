package com.hodinv.weatherforecast.database.services

import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.dao.WeatherDao

/**
 * Created by vasily on 21.03.18.
 */
class WeatherDbService(private val weatherDao: WeatherDao, private val placesService: PlacesDbService, private val notifyAboutChanges: () -> Unit) : WeatherService {
    override fun getWeatherInfo(cityId: Int): WeatherInfo {
        return weatherDao.getById(cityId) ?: WeatherInfo()
    }

    override fun putWeather(info: WeatherInfo) {
        val item = weatherDao.getById(info.id)
        if (item == null) {
            weatherDao.insertNew(info)
        } else {
            weatherDao.update(info)
        }
        placesService.setCityUpdateTimeToCurrent(info.id)
        notifyAboutChanges()
    }

    override fun getWeatherInfo(): List<WeatherInfo> {
        return weatherDao.getAll()
    }
}