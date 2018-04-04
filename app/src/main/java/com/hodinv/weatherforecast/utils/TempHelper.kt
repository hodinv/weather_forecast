package com.hodinv.weatherforecast.utils

/**
 * Created by vasily on 25.03.18.
 * Helper to convert temp value from server (Kelvins)
 */
class TempHelper {
    companion object {
        /**
         * Convert Kelvins to Celsius
         * @param t temperature in Kelvin
         * @return temperature in Celsius
         */
        fun kelvinToCelsuis(t: Float): Float = t - 273.15f
    }
}