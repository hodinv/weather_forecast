package com.hodinv.weatherforecast.database

import android.arch.persistence.room.Room
import android.content.Context
import com.hodinv.weatherforecast.database.repository.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by vasily on 18.03.18.
 * Holds connection to database
 */
class DatabaseProvider
private constructor(context: Context) : RepositoryUpdatesProvider {


    /**
     * Settings for Room database
     */
    private var db: AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    private var emitWeather: PublishSubject<Unit> = PublishSubject.create()
    private var emitForecast: PublishSubject<Int> = PublishSubject.create()

    /**
     * Returns DB implementation for places repository
     * @return places service
     */
    fun getPlacesService(): PlacesDbRepository {
        return PlacesDbRepository(db.placesDao(), db.weatherDao(), ::notifyWeatherListeners)
    }

    /**
     * Returns DB implementation for weather repository
     * @return weather service
     */
    fun getWeatherService(): WeatherDbRepository {
        return WeatherDbRepository(db.weatherDao(), getPlacesService(), ::notifyWeatherListeners)
    }

    /**
     * Returns DB implementation for forecast repository
     * @return forecast service
     */
    fun getForecastService(): ForecastRepository {
        return ForecastDbRepository(db.forecastDao(), ::notifyForecastListeners)
    }

    private fun notifyWeatherListeners() {
        emitWeather.onNext(Unit)
    }

    private fun notifyForecastListeners(cityId: Int) {
        emitForecast.onNext(cityId)
    }

    /**
     * Provides observable for changes in weather records
     * @return observable that will emit onNext on any changes in weather
     */
    override fun getWeatherUpdates(): Observable<Unit> {
        return emitWeather

    }

    /**
     * Provides observable for changes in forecast records
     * @return observable that will emit onNext on any changes in forecast for city, also passes cityId as parameter
     */
    override fun getForecastUpdate(): Observable<Int> {
        return emitForecast
    }


    companion object {
        /**
         * instance of Database provider
         */
        lateinit var instance: DatabaseProvider
            private set

        /**
         * Initialize instance of database provider. Should be called in Application.onCreate
         */
        fun initialize(context: Context) {
            instance = DatabaseProvider(context)
        }


    }

}