package com.hodinv.weatherforecast.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.data.WeatherInfo

/**
 * Created by vasily on 21.03.18.
 */
@Dao
interface WeatherDao {
    @Query("SELECT * FROM weatherInfo")
    fun getAll(): List<WeatherInfo>

    @Query("SELECT * FROM weatherInfo WHERE id = :id")
    fun getById(id: Int): WeatherInfo?

    @Insert
    fun insertNew(weatherInfo: WeatherInfo)

    @Update
    fun update(weatherInfo: WeatherInfo)

    @Query("DELETE FROM weatherInfo WHERE id = :id")
    fun deleteById(id: Int)
}