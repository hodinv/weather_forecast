package com.hodinv.weatherforecast.database.repository

import com.hodinv.weatherforecast.data.Place

/**
 * Created by vasily on 24.03.18.
 * Interface for places repository
 */
interface PlacesRepository {
    /**
     * Return all places
     * @return list of places
     */
    fun getPlaces(): List<Place>

    /**
     * Checks of city is in places list
     * @param id city id to check
     * @return true if city is in list
     */
    fun hasCity(id: Int): Boolean

    /**
     * Add city to list
     * @param cityId city to add to list
     */
    fun addCity(cityId: Int)

    /**
     * removes city from list
     * @param cityId city to remove from list
     */
    fun deleteCity(cityId: Int)

    /**
     * Sets last update time to current time
     * @param cityId city to set time
     */
    fun setCityUpdateTimeToCurrent(cityId: Int)
}