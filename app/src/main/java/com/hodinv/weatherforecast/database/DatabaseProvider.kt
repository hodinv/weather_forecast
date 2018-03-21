package com.hodinv.weatherforecast.database

import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.services.PlacesService
import com.hodinv.weatherforecast.database.services.WeatherService
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by vasily on 18.03.18.
 */
class DatabaseProvider {

    private var db: AppDatabase
    private val weatherListeners = CopyOnWriteArrayList<() -> Unit>()

    /*
        fun getCitiesList(): List<Int> {
        return listOf(588409, 2643743, 625144)
    }

     */
    private constructor(context: Context) {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() // todo: remove
                .build()
    }

    fun getPlacesService(): PlacesService {
        return PlacesService(db.placesDao())
    }

    fun getWeatherService(): WeatherService {
        return WeatherService(db.weatherDao(), getPlacesService(), this::notifyWeatherListeners)
    }

    fun notifyWeatherListeners() {
        weatherListeners.forEach { it() }
    }

    fun addWeatherListener(callback: () -> Unit) {
        weatherListeners.add(callback)
    }

    fun removeWeatherListener(callback: () -> Unit) {
        weatherListeners.remove(callback)
    }

    fun addForecastListener(cityId: Int, callback: () -> Unit) {

    }

    companion object {
        lateinit var instance: DatabaseProvider
            get
            private set

        fun initialize(context: Context) {
            instance = DatabaseProvider(context)
        }


    }
}