package com.hodinv.weatherforecast.data

import com.google.gson.annotations.SerializedName

/**
 * Created by vasily on 21.03.18.
 */
class MainInfo {
    var temp: Float = 0.0f
    var pressure: Float = 0f
    var humidity: Float = 0f
    @SerializedName("temp_min")
    var tempMin: Float = 0.0f
    @SerializedName("temp_max")
    var tempMax: Float = 0.0f
}