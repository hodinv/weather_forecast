package com.hodinv.weatherforecast.database.services

/**
 * Created by vasily on 24.03.18.
 */
interface WeatherUpdatesProvider {
    fun addWeatherListener(callback: () -> Unit)
    fun removeWeatherListener(callback: () -> Unit)
}