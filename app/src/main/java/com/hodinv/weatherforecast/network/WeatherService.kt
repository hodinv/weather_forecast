package com.hodinv.weatherforecast.network

import com.hodinv.weatherforecast.data.Forecast
import com.hodinv.weatherforecast.data.WeatherInfoFromNet
import com.hodinv.weatherforecast.network.api.WeatherApi
import io.reactivex.Observable

/**
 * Created by vasily on 18.03.18.
 */
class WeatherService(private val api: WeatherApi, private val key: String) {
    fun getWeather(city: String): Observable<WeatherInfoFromNet> {
        return api.getPlaceInfo(key, city)
    }

    fun getWeather(cityId: Int): Observable<WeatherInfoFromNet> {
        return api.getPlaceInfo(key, cityId)
    }

    fun getForecast(cityId: Int): Observable<Forecast> {
        return api.getForecast(key, cityId)
    }
}