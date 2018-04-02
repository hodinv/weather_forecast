package com.hodinv.weatherforecast.network

import com.hodinv.weatherforecast.network.api.WeatherApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by vasily on 18.03.18.
 */
class NetworkProvider(private val apiKey: String) {
    private val retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://api.openweathermap.org/")
            .build()

    fun getWeatherService(): WeatherService {
        return WeatherService(retrofit.create(WeatherApi::class.java), apiKey)
    }

}