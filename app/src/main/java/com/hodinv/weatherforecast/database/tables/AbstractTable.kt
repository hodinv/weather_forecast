package com.hodinv.weatherforecast.database.tables

/**
 * Created by vasily on 19.03.18.
 */
interface AbstractTable {
    val name: String
    val columns: Array<String>
    fun createSQL(): String
}