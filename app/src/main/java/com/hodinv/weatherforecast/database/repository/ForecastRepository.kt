package com.hodinv.weatherforecast.database.repository

import com.hodinv.weatherforecast.data.ForecastRecord

/**
 * Created by vasily on 26.03.18.
 * Interface for forecast repository
 */
interface ForecastRepository {
    /**
     * Update forecast
     * @param record Forecast record, contains city id
     */
    fun putForecast(record: ForecastRecord)

    /**
     * Get forecast for city by id
     * @return forecast for city, or empty object if no record
     */
    fun getForecast(cityId: Int): ForecastRecord
}