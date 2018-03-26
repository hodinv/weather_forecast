package com.hodinv.weatherforecast.network.api

import com.hodinv.weatherforecast.data.Forecast
import com.hodinv.weatherforecast.data.WeatherInfoFromNet
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by vasily on 18.03.18.
 */
interface WeatherApi {
    @GET("data/2.5/weather")
    fun getPlaceInfo(@Query("appid") appid: String, @Query("q") city: String): Observable<WeatherInfoFromNet>

    @GET("data/2.5/weather")
    fun getPlaceInfo(@Query("appid") appid: String, @Query("id") cityId: Int): Observable<WeatherInfoFromNet>

    @GET("data/2.5/forecast")
    fun getForecast(@Query("appid") appid: String, @Query("id") cityId: Int): Observable<Forecast>

}