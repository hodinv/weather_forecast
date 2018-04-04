package com.hodinv.weatherforecast.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.hodinv.weatherforecast.data.ForecastRecord

/**
 * Created by vasily on 26.03.18.
 * DAO definition for forecast
 */
@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecastRecord WHERE id = :id")
    fun getById(id: Int): ForecastRecord?

    @Insert
    fun insertNew(record: ForecastRecord)

    @Update
    fun update(record: ForecastRecord)
}