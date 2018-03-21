package com.hodinv.weatherforecast.database.dao

import android.arch.persistence.room.*
import com.hodinv.weatherforecast.data.Place


/**
 * Created by vasily on 21.03.18.
 */
@Dao
interface PlacesDao {
    @Query("SELECT * FROM place")
    fun getAll(): List<Place>

    @Insert
    fun addNew(place: Place)

    @Query("DELETE FROM place WHERE id = :arg0")
    fun deleteById(id: Int)

    @Update
    fun update(place: Place)
}