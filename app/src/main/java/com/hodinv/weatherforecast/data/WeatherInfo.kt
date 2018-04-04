package com.hodinv.weatherforecast.data

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 * Created by vasily on 18.03.18.
 * Weather model entity as it saved in db
 */
@Entity
open class WeatherInfo {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

    var name: String = ""

    @Embedded
    var wind: Wind = Wind()

    @Embedded
    var main: MainInfo = MainInfo()

    var weatherInfo: String = ""
}