package com.hodinv.weatherforecast.service

import io.reactivex.Observable

/**
 * Created by vasily on 21.03.18.
 * Network requests processor interface
 */
interface NetworkService {
    /**
     * Request weather for all cities that have place record in repository.
     * If timeout not pass since last request - no request is done
     * @param force if true - ignores timeout and perform request
     * @return false if already running request, else - true
     */
    fun requestWeather(force: Boolean = false): Boolean


    /**
     * Request forecast for city
     * @param cityId city to request forecast for
     * @return false if already running request for this city, else - true
     */
    fun requestForecast(cityId: Int): Boolean

    /**
     * Check if request for weather is running now
     * @return true if request is running
     */
    fun isWeatherRequestRunning(): Boolean

    /**
     * Return observer that emits values every time forecast request or weather request state changes
     * @return observer that trigger on any request state changes (start or stop)
     */
    fun getStateSubscription(): Observable<Unit>


    /**
     * Search for city by name and if found - adds it to repository with weather data
     * @param placeName city name to perform search
     * @return observer with result of search, emits true if added and false if not found or already exists
     */
    fun searchAndAddNewPlace(placeName: String): Observable<Boolean>

    /**
     * Check if forecast request is running for certain city
     * @param cityId id of city to check request state
     * @return true if request is running
     */
    fun isForecastRequestRunning(cityId: Int): Boolean
}