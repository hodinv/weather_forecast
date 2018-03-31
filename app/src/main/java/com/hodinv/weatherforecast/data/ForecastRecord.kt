package com.hodinv.weatherforecast.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters

/**
 * Created by vasily on 26.03.18.
 */
@Entity
@TypeConverters(ForecastConverter::class)
data class ForecastRecord(
        @PrimaryKey(autoGenerate = false)
        var id: Int = 0,

        var forecast: Forecast = Forecast()

)