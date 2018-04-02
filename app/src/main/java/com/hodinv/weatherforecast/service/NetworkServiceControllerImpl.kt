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
 * Process usecases for sending proper request to service. Including force reqfresh logic
 */
class NetworkServiceControllerImpl(val context: Context) : NetworkServiceController {
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
    /** Defines callbacks for service binding, passed to bindService()  */


    override fun waitForControllerReady(): Observable<Unit> {
        return emitReady
    }

    override fun getStateSubscription(): Observable<Unit> {
        return waitForControllerReady().flatMap { bondedService?.getStateSubscription() }
    }


    override fun requestWeather(force: Boolean): Boolean {
        return bondedService?.requestWeather(force) ?: false
    }

    override fun searchAndAddNewPlace(placeName: String): Observable<Boolean> {
        return bondedService?.searchAndAddNewPlace(placeName) ?: Observable.just(false)
    }


    override fun requestForecast(cityId: Int, force: Boolean): Boolean {
        return bondedService?.requestForecast(cityId, force) ?: false
    }


    override fun isWeatherRequestRunning(): Boolean {
        return bondedService?.isWeatherRequestRunning() ?: false
    }

    override fun isForecastRequestRunning(cityId: Int): Boolean {
        return bondedService?.isForecastRequestRunning(cityId) ?: false
    }

}