package com.hodinv.weatherforecast.database.repository

import com.hodinv.weatherforecast.data.ForecastRecord
import com.hodinv.weatherforecast.database.dao.ForecastDao

/**
 * Created by vasily on 26.03.18.
 * Implementation for forecast repository, using db as storage
 * @param forecastDao DAO for forecast
 * @param notifyAboutChanges callback to call if forecast was updated
 */
class ForecastDbRepository(private val forecastDao: ForecastDao, private val notifyAboutChanges: (cityId: Int) -> Unit) : ForecastRepository {
    /**
     * Update forecast
     * @param record Forecast record, contains city id
     */
    override fun putForecast(record: ForecastRecord) {
        if (forecastDao.getById(record.id) != null) {
            forecastDao.update(record)
        } else {
            forecastDao.insertNew(record)
        }
        notifyAboutChanges(record.id)
    }

    /**
     * Get forecast for city by id
     * @return forecast for city, or empty object if no record
     */
    override fun getForecast(cityId: Int): ForecastRecord {
        return forecastDao.getById(cityId) ?: ForecastRecord()
    }
}