package com.hodinv.weatherforecast.data

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson

/**
 * Created by vasily on 26.03.18.
 */
class ForecastConverter {
    @TypeConverter
    fun fromForecast(forecast: Forecast): String {
        return gson.toJson(forecast)
    }

    @TypeConverter
    fun toForecast(json: String): Forecast {
        return gson.fromJson(json, Forecast::class.java)
    }

    companion object {
        private val gson: Gson = Gson()
    }
}