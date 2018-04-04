package com.hodinv.weatherforecast.database.repository

import io.reactivex.Observable


/**
 * Created by vasily on 24.03.18.
 * Interface that provides updates observables for changes in model
 */
interface RepositoryUpdatesProvider {
    /**
     * Provides observable for changes in weather records
     * @return observable that will emit onNext on any changes in weather
     */
    fun getWeatherUpdates(): Observable<Unit>

    /**
     * Provides observable for changes in forecast records
     * @return observable that will emit onNext on any changes in forecast for city, also passes cityId as parameter
     */
    fun getForecastUpdate(): Observable<Int>
}