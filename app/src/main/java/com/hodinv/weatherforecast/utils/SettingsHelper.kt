package com.hodinv.weatherforecast.utils

import android.content.Context

/**
 * Created by vasily on 02.04.18.
 * Helper for saving values in shared prefs
 * @param context context for shared prefs
 */
class SettingsHelper(val context: Context) {

    /**
     * Set current selected city for forecast
     * @param cityId city to navigate to on next start
     */
    fun setCityId(cityId: Int) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().putInt(CITY_ID, cityId).apply()
    }

    /**
     * Removes record of current city
     */
    fun clearCity() {
        setCityId(NO_CITY)
    }

    /**
     * Return current city id to navigate to or #NO_CITY if not needed
     */
    fun getCity(): Int {
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt(CITY_ID, NO_CITY)
    }

    companion object {
        const val PREFS = "prefs"
        const val CITY_ID = "city_id"
        const val NO_CITY = -1
    }
}