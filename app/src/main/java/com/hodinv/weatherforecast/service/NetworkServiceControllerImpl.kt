package com.hodinv.weatherforecast.service

import android.content.Context
import android.content.Intent
import io.reactivex.Observable
import android.content.ComponentName
import com.hodinv.weatherforecast.service.NetworkRequestsPerformer.LocalBinder
import android.os.IBinder
import android.content.ServiceConnection
import java.util.concurrent.CopyOnWriteArrayList


/**
 * Created by vasily on 18.03.18.
 * Process usecases for sending proper request to service. Including force reqfresh logic
 */
class NetworkServiceControllerImpl(val context: Context) : NetworkServiceController {

    private val callbacks = CopyOnWriteArrayList<() -> Unit>()

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName,
                                        service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as LocalBinder
            bondedService = binder.getService()
            callbacks.forEach { it() }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bondedService = null
        }
    }


    init {
        val intent = Intent(context, NetworkRequestsPerformer::class.java);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    private var bondedService: NetworkService? = null
    /** Defines callbacks for service binding, passed to bindService()  */


    override fun waitForControllerReady(): Observable<Unit> {
        if (bondedService != null)
            return Observable.just(Unit)
        return Observable.create { consumer ->
            // thread may changed
            if (bondedService != null) {
                consumer.onNext(Unit)
            } else {
                callbacks.add {
                    consumer.onNext(Unit)
                }
            }
        }
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun isWeatherRequestRunning(): Boolean {
        return bondedService?.isWeatherRequestRunning() ?: false
    }

    override fun isForecastRequestRunning(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}