package com.hodinv.weatherforecast.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.hodinv.weatherforecast.data.ForecastRecord
import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.dao.ForecastDao
import com.hodinv.weatherforecast.database.dao.PlacesDao
import com.hodinv.weatherforecast.database.dao.WeatherDao

/**
 * Created by vasily on 21.03.18.
 * Holds database meta information and entities list.
 */
@Database(version = 7, entities = [(Place::class), (WeatherInfo::class), (ForecastRecord::class)], exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placesDao(): PlacesDao
    abstract fun weatherDao(): WeatherDao
    abstract fun forecastDao(): ForecastDao
}