package com.hodinv.weatherforecast.data

/**
 * Created by vasily on 26.03.18.
 * Model entity
 */
class Forecast {
    var city: City = City()
    var list: Array<ForecastItem> = emptyArray()
}


