package com.hodinv.weatherforecast.database

import android.arch.persistence.room.Room
import android.content.Context
import com.hodinv.weatherforecast.database.services.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by vasily on 18.03.18.
 */
class DatabaseProvider
private constructor(context: Context) : WeatherUpdatesProvider {


    private var db: AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
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
    }

    private fun notifyForecastListeners(cityId: Int) {
        emitForecast.onNext(cityId)
    }

    override fun getWeatherUpdates(): Observable<Unit> {
        return emitWeather

    }

    override fun getForecastUpdate(): Observable<Int> {
        return emitForecast
    }


    companion object {
        lateinit var instance: DatabaseProvider
            private set

        fun initialize(context: Context) {
            instance = DatabaseProvider(context)
        }


    }

}