package com.hodinv.weatherforecast.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.ForecastRecord
import com.hodinv.weatherforecast.database.DatabaseProvider
import com.hodinv.weatherforecast.network.NetworkProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.lang.ref.WeakReference
import java.util.concurrent.ConcurrentSkipListSet
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Makes background calls to weather service and puts all weather data database
 * Call to town is performed every 10 minutes or if it was forced
 */
class NetworkRequestsPerformer : Service(), NetworkService {


    override fun isForecastRequestRunning(cityId: Int): Boolean {
        return forecastsRunning.contains(cityId)
    }

    override fun searchAndAddNewPlace(placeName: String): Observable<Boolean> {
        if (addingRequestIsRunning)
            return Observable.just(false);
        addingRequestIsRunning = true;
        notifyWeatherRequest()
        return networkProvider.getWeatherService().getWeather(placeName)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError {
                    addingRequestIsRunning = false
                    notifyWeatherRequest()
                }
                .flatMap {
                    val found = databaseProvider.getPlacesService().hasCity(it.id)
                    databaseProvider.getWeatherService().putWeather(it.getDbWeatherInfo())
                    if (!found) {
                        databaseProvider.getPlacesService().addCity(it.id)
                    }
                    addingRequestIsRunning = false
                    notifyWeatherRequest()
                    Observable.just(!found)
                }

    }

    override fun getStateSubscription(): Observable<Unit> {
        return emitWeather
    }

    override fun isWeatherRequestRunning(): Boolean {
        return weatherRequestIsRunning || addingRequestIsRunning
    }

    var weatherRequestIsRunning = false;
    var addingRequestIsRunning = false;
    var forecastsRunning = ConcurrentSkipListSet<Int>()
    var emitWeather: PublishSubject<Unit> = PublishSubject.create()


    override fun requestWeather(force: Boolean): Boolean {
        if (weatherRequestIsRunning)
            return false;
        weatherRequestIsRunning = true;
        notifyWeatherRequest()
        databaseProvider.getPlacesService().getPlaces().toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .filter { it.updated < System.currentTimeMillis() - REFRESH_TIME || force }
                .switchMap {
                    networkProvider.getWeatherService().getWeather(it.id)
                }

                //.observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            databaseProvider.getWeatherService().putWeather(result.getDbWeatherInfo())
                        },
                        { error ->
                            error.printStackTrace()
                            weatherRequestIsRunning = false;
                            notifyWeatherRequest()
                        },
                        {
                            Log.d("ISMAIN", "=" + (Looper.myLooper() == Looper.getMainLooper()))
                            weatherRequestIsRunning = false;
                            notifyWeatherRequest()
                        });
        return true;
    }

    private fun notifyWeatherRequest() {
        emitWeather.onNext(Unit)
    }

    override fun requestForecast(cityId: Int, force: Boolean): Boolean {
        if (forecastsRunning.contains(cityId))
            return false
        forecastsRunning.add(cityId)
        notifyWeatherRequest()
        networkProvider.getWeatherService().getForecast(cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnError {
                    forecastsRunning.remove(cityId)
                    notifyWeatherRequest()
                    Log.d("NET", "notify no more for $cityId")
                }
                .subscribe({ result ->
                    databaseProvider.getForecastService().putForecast(ForecastRecord(result.city.id, result))
                    forecastsRunning.remove(cityId)
                    notifyWeatherRequest()
                    Log.d("NET", "notify no more for $cityId")
                })
        return true;
    }


    lateinit var networkProvider: NetworkProvider
    val databaseProvider = DatabaseProvider.instance
    val binder: IBinder = LocalBinder()

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): NetworkService {
            return this@NetworkRequestsPerformer
        }
    }

    override fun onCreate() {
        super.onCreate()
        networkProvider = NetworkProvider(getString(R.string.weather_api))
    }


    companion object {
        val REFRESH_TIME = 1000 * 60 * 5;// 5 min
    }
}
