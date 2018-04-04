package com.hodinv.weatherforecast.service

import io.reactivex.Observable


/**
 * Created by vasily on 18.03.18.
 * Interface to work with helper for network service binder
 */
interface NetworkServiceController : NetworkService {
    /**
     * emit value when service is binded and ready to receive requests
     * @return observer that will emit one onNext and one onComplete after binding
     */
    fun waitForControllerReady(): Observable<Unit>

    /**
     * close connection to servers (unbind)
     */
    fun close()
}