package com.hodinv.weatherforecast.database

import android.arch.persistence.room.Room
import android.content.Context
import android.util.Log
import com.hodinv.weatherforecast.database.services.PlacesDbService
import com.hodinv.weatherforecast.database.services.WeatherDbService
import com.hodinv.weatherforecast.database.services.WeatherUpdatesProvider
import io.reactivex.Observable
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created by vasily on 18.03.18.
 */
class DatabaseProvider : WeatherUpdatesProvider {


    private var db: AppDatabase
    private val weatherListeners = CopyOnWriteArrayList<WeakReference<() -> Unit>>()
    private val forecastListeners = CopyOnWriteArrayList<WeakReference<() -> Unit>>()

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
        return PlacesDbService(db.placesDao(), db.weatherDao(), ::notifyWeatherListeners)
    }

    fun getWeatherService(): WeatherDbService {
        return WeatherDbService(db.weatherDao(), getPlacesService(), ::notifyWeatherListeners)
    }

    private fun notifyWeatherListeners() {
        Log.d("DB", "notify, listeners size = " + weatherListeners.size)
        weatherListeners.removeAll { it.get() == null }
        Log.d("DB", "notify, listeners size after clean = " + weatherListeners.size)
        weatherListeners.forEach { it.get()?.invoke() }
    }


    override fun getWeatherUpdates(): Observable<Unit> {
        return Observable.create { consumer ->
            weatherListeners.add(WeakReference({ consumer.onNext(Unit) }))

        }
    }

    override fun getForecastUpdate(cityId: Int): Observable<Unit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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