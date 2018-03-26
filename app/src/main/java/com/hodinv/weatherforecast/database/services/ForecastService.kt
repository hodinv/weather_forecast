package com.hodinv.weatherforecast.database.services

import com.hodinv.weatherforecast.data.ForecastRecord

/**
 * Created by vasily on 26.03.18.
 */
interface ForecastService {
    fun putForecast(record: ForecastRecord)
    fun getForecast(cityId: Int): ForecastRecord
}