package com.hodinv.weatherforecast.network

import com.hodinv.weatherforecast.data.Forecast
import com.hodinv.weatherforecast.data.WeatherInfoFromNet
import com.hodinv.weatherforecast.network.api.WeatherApi
import io.reactivex.Observable

/**
 * Created by vasily on 18.03.18.
 * Service that returns weather and forecast data from network
 * @param api retrofit api implementation
 * @param key application key for weather service
 * @param lang language code to pass in request
 */
class WeatherService(private val api: WeatherApi, private val key: String, private val lang: String) {
    /**
     * Return weather for city
     * @param city city name to perform search
     * @return observable with weather info response as it comes from net
     */
    fun getWeather(city: String): Observable<WeatherInfoFromNet> {
        return api.getPlaceInfo(key, lang, city)
    }

    /**
     * Return weather for city
     * @param cityId city id to perform search
     * @return observable with weather info response as it comes from net
     */
    fun getWeather(cityId: Int): Observable<WeatherInfoFromNet> {
        return api.getPlaceInfo(key, lang, cityId)
    }

    /**
     * Return weather for city
     * @param cityId city id to get forecast for
     * @return observable with forecast
     */    fun getForecast(cityId: Int): Observable<Forecast> {
        return api.getForecast(key, lang, cityId)
    }
}