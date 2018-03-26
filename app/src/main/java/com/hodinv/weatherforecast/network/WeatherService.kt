package com.hodinv.weatherforecast.network

import com.hodinv.weatherforecast.data.WeatherInfoFromNet
import com.hodinv.weatherforecast.network.api.WeatherApi
import io.reactivex.Observable

/**
 * Created by vasily on 18.03.18.
 */
class WeatherService(val api: WeatherApi, val key: String) {
    fun getWeather(city: String): Observable<WeatherInfoFromNet> {
        return api.getPlaceInfo(key, city)
    }

    fun getWeather(cityId: Int): Observable<WeatherInfoFromNet> {
        return api.getPlaceInfo(key, cityId)
    }
}