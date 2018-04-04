package com.hodinv.weatherforecast.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.hodinv.weatherforecast.service.NetworkRequestsPerformer.LocalBinder
import io.reactivex.Observable
import io.reactivex.subjects.ReplaySubject


/**
 * Created by vasily on 18.03.18.
 * Process use cases for sending proper request to service. Including force refresh logic
 * Binding to service is done in constructor
 */
class NetworkServiceControllerImpl(val context: Context) : NetworkServiceController {

    /**
     * close connection to servers (unbind)
     */
    override fun close() {
        waitForControllerReady().subscribe { context.unbindService(connection) }
    }

    var emitReady: ReplaySubject<Unit> = ReplaySubject.create()

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName,
                                        service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as LocalBinder
            bondedService = binder.getService()
            emitReady.onNext(Unit)
            emitReady.onComplete()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bondedService = null
        }
    }


    init {
        val intent = Intent(context, NetworkRequestsPerformer::class.java)
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    private var bondedService: NetworkService? = null

    /**
     * emit value when service is bound and ready to receive requests
     * @return observer that will emit one onNext and one onComplete after binding
     */
    override fun waitForControllerReady(): Observable<Unit> {
        return emitReady
    }

    /**
     * Return observer that emits values every time forecast request or weather request state changes
     * @return observer that trigger on any request state changes (start or stop)
     */
    override fun getStateSubscription(): Observable<Unit> {
        return waitForControllerReady().flatMap { bondedService?.getStateSubscription() }
    }

    /**
     * Request weather for all cities that have place record in repository.
     * If timeout not pass since last request - no request is done
     * @param force if true - ignores timeout and perform request
     * @return false if already running request, else - true
     */
    override fun requestWeather(force: Boolean): Boolean {
        return bondedService?.requestWeather(force) ?: false
    }

    /**
     * Search for city by name and if found - adds it to repository with weather data
     * @param placeName city name to perform search
     * @return observer with result of search, emits true if added and false if not found or already exists
     */
    override fun searchAndAddNewPlace(placeName: String): Observable<Boolean> {
        return bondedService?.searchAndAddNewPlace(placeName) ?: Observable.just(false)
    }

    /**
     * Request forecast for city
     * @param cityId city to request forecast for
     * @return false if already running request for this city, else - true
     */
    override fun requestForecast(cityId: Int): Boolean {
        return bondedService?.requestForecast(cityId) ?: false
    }

    /**
     * Check if request for weather is running now
     * @return true if request is running
     */
    override fun isWeatherRequestRunning(): Boolean {
        return bondedService?.isWeatherRequestRunning() ?: false
    }

    /**
     * Check if forecast request is running for certain city
     * @param cityId id of city to check request state
     * @return true if request is running
     */
    override fun isForecastRequestRunning(cityId: Int): Boolean {
        return bondedService?.isForecastRequestRunning(cityId) ?: false
    }

}