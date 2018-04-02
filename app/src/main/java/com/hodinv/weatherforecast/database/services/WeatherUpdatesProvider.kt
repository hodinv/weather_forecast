package com.hodinv.weatherforecast.database.services

import io.reactivex.Observable


/**
 * Created by vasily on 24.03.18.
 */
interface WeatherUpdatesProvider {
    fun getWeatherUpdates(): Observable<Unit>
    fun getForecastUpdate(): Observable<Int>
}