package com.hodinv.weatherforecast.database.services

import com.hodinv.weatherforecast.data.Place

/**
 * Created by vasily on 24.03.18.
 */
interface PlacesService {
    fun getPlaces(): List<Place>
    fun addCity(cityId: Int)
    fun deleteCity(cityId: Int)
    fun setCityUpdateTimeToCurrent(cityId: Int)
}