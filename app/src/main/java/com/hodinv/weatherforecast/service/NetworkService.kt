package com.hodinv.weatherforecast.service

import io.reactivex.Observable

/**
 * Created by vasily on 21.03.18.
 */
interface NetworkService {
    fun requestWeather(force: Boolean = false): Boolean
    fun requestForecast(cityId: Int, force: Boolean = false): Boolean
    fun isWeatherRequestRunning(): Boolean
    fun getStateSubscription(): Observable<Unit>
}