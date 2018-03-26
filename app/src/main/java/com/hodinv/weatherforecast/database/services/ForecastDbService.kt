package com.hodinv.weatherforecast.database.services

import com.hodinv.weatherforecast.data.ForecastRecord
import com.hodinv.weatherforecast.database.dao.ForecastDao

/**
 * Created by vasily on 26.03.18.
 */
class ForecastDbService(val forecastDao: ForecastDao) : ForecastService {
    override fun putForecast(record: ForecastRecord) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getForecast(cityId: Int): ForecastRecord {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}