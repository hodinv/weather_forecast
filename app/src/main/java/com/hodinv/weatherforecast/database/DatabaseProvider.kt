package com.hodinv.weatherforecast.database

import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import com.hodinv.weatherforecast.database.services.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by vasily on 18.03.18.
 */
class DatabaseProvider// todo: remove
private constructor(context: Context) : WeatherUpdatesProvider {


    private var db: AppDatabase
    private var emitWeather: PublishSubject<Unit> = PublishSubject.create()
    private var emitForecast: PublishSubject<Int> = PublishSubject.create()

    fun getPlacesService(): PlacesDbService {
        return PlacesDbService(db.placesDao(), db.weatherDao(), ::notifyWeatherListeners)
    }

    fun getWeatherService(): WeatherDbService {
        return WeatherDbService(db.weatherDao(), getPlacesService(), ::notifyWeatherListeners)
    }

    fun getForecastService(): ForecastService {
        return ForecastDbService(db.forecastDao(), ::notifyForecastListeners)
    }

    private fun notifyWeatherListeners() {
        emitWeather.onNext(Unit)
        Log.d("DB", "notify weather")
    }

    private fun notifyForecastListeners(cityId: Int) {
        emitForecast.onNext(cityId)
        Log.d("DB", "notify forecast for $cityId")
    }

    override fun getWeatherUpdates(): Observable<Unit> {
        return emitWeather

    }

    override fun getForecastUpdate(): Observable<Int> {
        return emitForecast
    }


    companion object {
        lateinit var instance: DatabaseProvider
            get
            private set

        fun initialize(context: Context) {
            instance = DatabaseProvider(context)
        }


    }

    init {
        db = Room.databaseBuilder(context, AppDatabase::class.java, "database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries() // todo: remove
                .build()
    }
}