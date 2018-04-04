package com.hodinv.weatherforecast.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 * Created by vasily on 17.03.18.
 * Place DB Model entity - holds when city was updated, and also marker that city is in list
 */
@Entity
data class Place(
        @PrimaryKey(autoGenerate = false)
        var id: Int,

        var updated: Long
)