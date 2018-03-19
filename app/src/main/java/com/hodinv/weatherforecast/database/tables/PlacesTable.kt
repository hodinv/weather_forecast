package com.hodinv.weatherforecast.database.tables

/**
 * Created by vasily on 19.03.18.
 */
class PlacesTable : AbstractTable {
    override val columns: Array<String>
        get() = arrayOf(FIELD_ID)
    override val name: String
        get() = "Places"


    override fun createSQL(): String {
        return "CREATE TABLE " + name + "(" + FIELD_ID + " int)";
    }

    companion object {
        val FIELD_ID = "ID"
    }
}