package com.hodinv.weatherforecast.service

import io.reactivex.Observable


/**
 * Created by vasily on 18.03.18.
 */
interface NetworkServiceController : NetworkService {
    fun waitForControllerReady(): Observable<Unit>
}