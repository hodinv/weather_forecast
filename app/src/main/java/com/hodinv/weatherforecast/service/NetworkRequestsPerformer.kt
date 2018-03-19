package com.hodinv.weatherforecast.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.database.DatabaseProvider
import com.hodinv.weatherforecast.network.NetworkProvider
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers

/**
 * Makes background calls to weather service and puts all weather data database
 * Call to town is performed every 10 minutes or if it was forced
 */
class NetworkRequestsPerformer : Service() {


    lateinit var networkProvider: NetworkProvider
    val databaseProvider = DatabaseProvider.instance

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        networkProvider = NetworkProvider(getString(R.string.weather_api))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            GET_WEATHR_ACTION -> {
                databaseProvider.getPlacesService().getCitiesIdsList().toObservable()
                        .observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io())
                        .filter { databaseProvider.getPlacesService().getCityUpdateTime(it) < System.currentTimeMillis() - REFRESH_TIME }
                        .switchMap {
                            networkProvider.getWeatherService().getWeather(it)
                        }
                        .subscribe({ result ->
                            databaseProvider.putWeather(result.id, result, System.currentTimeMillis())
                        }, { error ->
                            error.printStackTrace()
                        })
            }
        }
        return START_NOT_STICKY;
    }

    companion object {
        val GET_WEATHR_ACTION = "com.hodinv.weatherforecast.GET_WEATHER"
        val GET_FORECAST_ACTION = "com.hodinv.weatherforecast.GET_FORECAST"
        val REFRESH_TIME = 1000 * 60 * 5;// 5 min
    }
}
