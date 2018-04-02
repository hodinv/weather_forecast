package com.hodinv.weatherforecast.utils

import android.content.Context

/**
 * Created by vasily on 02.04.18.
 */
class SettingsHelper(val context: Context) {
    fun setCityId(cityId: Int) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putInt(CITY_ID, cityId).apply()
    }

    fun clearCity() {
        setCityId(NO_CITY)
    }

    fun getCity(): Int {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt(CITY_ID, NO_CITY)
    }

    companion object {
        const val PREFS = "prefs"
        const val CITY_ID = "city_id"
        const val NO_CITY = -1
    }
}