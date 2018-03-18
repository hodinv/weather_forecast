package com.hodinv.weatherforecast.network.api

import com.hodinv.weatherforecast.data.WeatherInfo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by vasily on 18.03.18.
 */
interface WeatherApi {
    @GET("data/2.5/weather")
    fun getPlaceInfo(@Query("appid") appid: String, @Query("q") city: String): Observable<WeatherInfo>

    @GET("data/2.5/weather")
    fun getPlaceInfo(@Query("appid") appid: String, @Query("id") cityId: Int): Observable<WeatherInfo>

}