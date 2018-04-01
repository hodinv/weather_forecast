package com.hodinv.weatherforecast.database.services

import android.util.Log
import com.hodinv.weatherforecast.data.ForecastRecord
import com.hodinv.weatherforecast.database.dao.ForecastDao

/**
 * Created by vasily on 26.03.18.
 */
class ForecastDbService(val forecastDao: ForecastDao, val notifyAboutChanges: (cityId: Int) -> Unit) : ForecastService {
    override fun putForecast(record: ForecastRecord) {
        if (forecastDao.getById(record.id) != null) {
            Log.d("forecast", "-update")
            forecastDao.update(record)
        } else {
            forecastDao.insertNew(record)
            Log.d("forecast", "-insert")
        }
        notifyAboutChanges(record.id)
    }

    override fun getForecast(cityId: Int): ForecastRecord {
        return forecastDao.getById(cityId) ?: ForecastRecord()
    }
}