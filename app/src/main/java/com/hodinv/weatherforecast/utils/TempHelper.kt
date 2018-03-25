package com.hodinv.weatherforecast.utils

/**
 * Created by vasily on 25.03.18.
 */
class TempHelper {
    companion object {
        fun kelvinToCelsuis(t: Float): Float = t - 273.15f
    }
}