package com.hodinv.weatherforecast.database

import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import com.hodinv.weatherforecast.database.services.PlacesDbService
import com.hodinv.weatherforecast.database.services.WeatherDbService
import com.hodinv.weatherforecast.database.services.WeatherUpdatesProvider
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by vasily on 18.03.18.
 */
class DatabaseProvider : WeatherUpdatesProvider {

    private var db: AppDatabase
    private val weatherListeners = CopyOnWriteArrayList<() -> Unit>()

    /*
        fun getCitiesList(): List<Int> {
        return listOf(588409, 2643743, 625144)
    }

     */
    private constructor (context: Context) {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() // todo: remove
                .build()
    }

    fun getPlacesService(): PlacesDbService {
        return PlacesDbService(db.placesDao())
    }

    fun getWeatherService(): WeatherDbService {
        return WeatherDbService(db.weatherDao(), getPlacesService(), this::notifyWeatherListeners)
    }

    fun notifyWeatherListeners() {
        Log.d("DB", "notify")
        weatherListeners.forEach { it() }
    }

    //todo:: make rx
    override fun addWeatherListener(callback: () -> Unit) {
        weatherListeners.add(callback)
    }

    override fun removeWeatherListener(callback: () -> Unit) {
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