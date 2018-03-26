package com.hodinv.weatherforecast.data

/**
 * Created by vasily on 26.03.18.
 */
class ForecastItem {
    var dt: Long = 0L
    var main: MainInfo = MainInfo()
    var weather: Weather = Weather()
    var wind: Wind = Wind()
}