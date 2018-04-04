package com.hodinv.weatherforecast.database.repository

import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.dao.WeatherDao

/**
 * Created by vasily on 21.03.18.
 * Implementation for weather repository, using db as storage
 * @param weatherDao Weather DAO object
 * @param placesService implementation for places service
 * @param notifyAboutChanges callback to notify when changes are done to weather record in model
 */
class WeatherDbRepository(private val weatherDao: WeatherDao, private val placesService: PlacesDbRepository, private val notifyAboutChanges: () -> Unit) : WeatherRepository {
    /**
     * Return weather info for certain city
     * @param cityId id of city
     * @return weather info record
     */
    override fun getWeatherInfo(cityId: Int): WeatherInfo {
        return weatherDao.getById(cityId) ?: WeatherInfo()
    }

    /**
     * Adds weather info
     * @param info weather info, city is is inside record
     */
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

    /**
     * Return list of weather info
     * @return list of recent weather info
     */
    override fun getWeatherInfo(): List<WeatherInfo> {
        return weatherDao.getAll()
    }
}