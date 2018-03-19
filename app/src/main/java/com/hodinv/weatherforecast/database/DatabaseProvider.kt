package com.hodinv.weatherforecast.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.services.PlacesService

/**
 * Created by vasily on 18.03.18.
 */
class DatabaseProvider {

    var helper: DatabaseHelper
    var db: SQLiteDatabase

    /*
        fun getCitiesList(): List<Int> {
        return listOf(588409, 2643743, 625144)
    }

     */
    private constructor(context: Context) {
        helper = DatabaseHelper(context)
        db = helper.writableDatabase
    }

    fun getPlacesService(): PlacesService {
        return PlacesService(db)
    }


    fun putWeather(cityId: Int, info: WeatherInfo, time: Long) {
        Log.d("WEATHER", "for " + info.name + " id = " + cityId)
    }


    companion object {
        lateinit var instance: DatabaseProvider
            get
            private set

        fun initialize(context: Context) {
            instance = DatabaseProvider(context)
        }


    }
}