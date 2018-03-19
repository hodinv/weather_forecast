package com.hodinv.weatherforecast.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.hodinv.weatherforecast.database.tables.AbstractTable
import com.hodinv.weatherforecast.database.tables.PlacesTable

/**
 * Created by vasily on 19.03.18.
 */
class DatabaseHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        for (table in TABLES) {
            db?.execSQL(table.createSQL())
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("DB", "upgrade from " + oldVersion + " to " + newVersion)
        for (table in TABLES) {
            db?.execSQL("DROP TABLE " + table.name)
        }
        for (table in TABLES) {
            db?.execSQL(table.createSQL())
        }
    }

    companion object {
        val VERSION = 1
        val DB_NAME = "db_weather"
        val TABLES: Array<AbstractTable> = arrayOf(PlacesTable())
    }
}