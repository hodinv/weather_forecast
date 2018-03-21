package com.hodinv.weatherforecast.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 * Created by vasily on 17.03.18.
 */
@Entity
data class Place(
        @PrimaryKey(autoGenerate = false)
        var id: Int,

        var updated: Long
)