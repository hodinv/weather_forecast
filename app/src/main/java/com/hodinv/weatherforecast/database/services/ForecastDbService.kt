package com.hodinv.weatherforecast.database.services

import com.hodinv.weatherforecast.data.ForecastRecord
import com.hodinv.weatherforecast.database.dao.ForecastDao

/**
 * Created by vasily on 26.03.18.
 */
class ForecastDbService(val forecastDao: ForecastDao, val notifyAboutChanges: () -> Unit) : ForecastService {
    override fun putForecast(record: ForecastRecord) {
        if (forecastDao.getById(record.id) != null) {
            forecastDao.update(record)
        } else {
            forecastDao.insertNew(record)
        }
        notifyAboutChanges()
    }

    override fun getForecast(cityId: Int): ForecastRecord {
        return forecastDao.getById(cityId) ?: ForecastRecord()
    }
}