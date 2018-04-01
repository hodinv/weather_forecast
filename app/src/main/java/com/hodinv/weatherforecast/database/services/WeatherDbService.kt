package com.hodinv.weatherforecast.database.services

import android.util.Log
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.dao.WeatherDao

/**
 * Created by vasily on 21.03.18.
 */
class WeatherDbService(val weatherDao: WeatherDao, val placesService: PlacesDbService, val notifyAboutChanges: () -> Unit) : WeatherService {
    override fun getWeatherInfo(cityId: Int): WeatherInfo {
        return weatherDao.getById(cityId) ?: WeatherInfo()
    }

    override fun putWeather(info: WeatherInfo) {
        Log.d("Weather", "put")
        val item = weatherDao.getById(info.id);
        if (item == null) {
            Log.d("Weather", "-insert")
            weatherDao.insertNew(info)
        } else {
            Log.d("Weather", "-update")
            weatherDao.update(info)
        }
        placesService.setCityUpdateTimeToCurrent(info.id)
        notifyAboutChanges()
    }

    override fun getWeatherInfo(): List<WeatherInfo> {
        return weatherDao.getAll()
    }
}