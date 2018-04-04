package com.hodinv.weatherforecast.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.ForecastRecord
import com.hodinv.weatherforecast.database.DatabaseProvider
import com.hodinv.weatherforecast.network.NetworkProvider
import io.reactivex.Observable
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.ConcurrentSkipListSet

/**
 * Created by vasily
 * Makes background calls to weather service and puts all weather data database
 * Call to town is performed every 10 minutes or if it was forced
 */
class NetworkRequestsPerformer : Service(), NetworkService {

    /**
     * Check if fprecast reauest is runngin for certain city
     * @param cityId id of city to check request state
     * @return true if reauest is running
     */
    override fun isForecastRequestRunning(cityId: Int): Boolean {
        return forecastsRunning.contains(cityId)
    }

    /**
     * Search for city by name and if found - adds it to repository with weather data
     * @param placeName city name to perform search
     * @return observer with result of serach, emits true if added and false if not found or already exists
     */
    override fun searchAndAddNewPlace(placeName: String): Observable<Boolean> {
        if (addingRequestIsRunning)
            return Observable.just(false)
        addingRequestIsRunning = true
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

    /**
     * Return observer that emits values every time forecast reauest or weather request state changes
     * @return observer that trigger on any request state changes (start or stop)
     */
    override fun getStateSubscription(): Observable<Unit> {
        return emitWeather
    }

    /**
     * Check if rquest for weatehr is running now
     * @return true if request is running
     */
    override fun isWeatherRequestRunning(): Boolean {
        return weatherRequestIsRunning || addingRequestIsRunning
    }

    private var weatherRequestIsRunning = false
    private var addingRequestIsRunning = false
    private var forecastsRunning = ConcurrentSkipListSet<Int>()
    private var emitWeather: PublishSubject<Unit> = PublishSubject.create()


    /**
     * Request weather for all cities that have place record in repository.
     * If timeout not pass since last request - no request is done
     * @param force if true - ignores timeout and perform request
     * @return false if already running request, else - true
     */
    override fun requestWeather(force: Boolean): Boolean {
        if (weatherRequestIsRunning)
            return false
        weatherRequestIsRunning = true
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
                            weatherRequestIsRunning = false
                            notifyWeatherRequest()
                        },
                        {
                            weatherRequestIsRunning = false
                            notifyWeatherRequest()
                        })
        return true
    }

    private fun notifyWeatherRequest() {
        emitWeather.onNext(Unit)
    }

    /**
     * Request forecast for city
     * @param cityId city to request forecast for
     * @return false if already running requestfor this city, else - true
     */
    override fun requestForecast(cityId: Int): Boolean {
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
                }
                .subscribe({ result ->
                    databaseProvider.getForecastService().putForecast(ForecastRecord(result.city.id, result))
                    forecastsRunning.remove(cityId)
                    notifyWeatherRequest()
                }, { _ ->
                    forecastsRunning.remove(cityId)
                    notifyWeatherRequest()
                })
        return true
    }


    private lateinit var networkProvider: NetworkProvider
    private val databaseProvider = DatabaseProvider.instance
    private val binder: IBinder = LocalBinder()

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
        networkProvider = NetworkProvider(getString(R.string.weather_api), getString(R.string.code))
    }


    companion object {
        const val REFRESH_TIME = 1000 * 60 * 5// 5 min
    }
}
