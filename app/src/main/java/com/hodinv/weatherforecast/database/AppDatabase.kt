package com.hodinv.weatherforecast.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.dao.PlacesDao
import com.hodinv.weatherforecast.database.dao.WeatherDao

/**
 * Created by vasily on 21.03.18.
 */
@Database(version = 5, entities = arrayOf(Place::class, WeatherInfo::class), exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun placesDao(): PlacesDao
    abstract fun weatherDao(): WeatherDao
}