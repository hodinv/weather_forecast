package com.hodinv.weatherforecast.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.hodinv.weatherforecast.data.Place


/**
 * Created by vasily on 21.03.18.
 *  DAO definition for places
 */
@Dao
interface PlacesDao {
    @Query("SELECT * FROM place")
    fun getAll(): List<Place>

    @Insert
    fun addNew(place: Place)

    @Query("DELETE FROM place WHERE id = :id")
    fun deleteById(id: Int)

    @Update
    fun update(place: Place)
}