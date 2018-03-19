package com.hodinv.weatherforecast.database.services

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.hodinv.weatherforecast.database.tables.PlacesTable

/**
 * Created by vasily on 19.03.18.
 */
class PlacesService(val db: SQLiteDatabase) {

    fun getCitiesIdsList(): List<Int> {
        val query = db.query(PlacesTable().name, PlacesTable().columns, null, null, null, null, null)
        val result = ArrayList<Int>()
        query.moveToFirst()
        val indexId = query.getColumnIndex(PlacesTable.FIELD_ID)
        do {
            result.add(query.getInt(indexId))
        } while (query.moveToNext())
        return result
    }


    fun addCity(cityId: Int) {
        val values = ContentValues()
        values.put(PlacesTable.FIELD_ID, cityId)
        db.beginTransaction()
        db.insert(PlacesTable().name, null, values)
        db.setTransactionSuccessful()
        db.endTransaction()
    }

    fun deleteCity(cityId: Int) {

    }

    fun getCityUpdateTime(cityId: Int): Long {
        return 0L;
    }

    fun setCityUpdateTime(cityId: Int) {

    }


}
