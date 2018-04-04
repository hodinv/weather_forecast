package com.hodinv.weatherforecast.database.repository

import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.database.dao.PlacesDao
import com.hodinv.weatherforecast.database.dao.WeatherDao


/**
 * Created by vasily on 19.03.18.
 * Implementation for places repository, using db as storage
 * @param placesDao DAO object for places
 * @param weatherDao DAO object for weather record (used for removal)
 * @param notifyAboutChanges calback to notify when places list or update time was changed
 */
class PlacesDbRepository(private val placesDao: PlacesDao, private val weatherDao: WeatherDao, private val notifyAboutChanges: () -> Unit) : PlacesRepository {

    /**
     * Checks of city is in places list
     * @param id city id to check
     * @return true if city is in list
     */
    override fun hasCity(id: Int): Boolean {
        val items = placesDao.getAll()
        var result = false
        items.forEach { if (it.id == id) result = true }
        return result
    }

    /**
     * Return all places
     * @return list of places
     */
    override fun getPlaces(): List<Place> {
        return placesDao.getAll()
    }

    /**
     * Add city to list
     * @param cityId city to add to list
     */
    override fun addCity(cityId: Int) {
        val place = Place(id = cityId, updated = 0L)
        placesDao.addNew(place)
    }

    /**
     * removes city from list
     * @param cityId city to remove from list
     */
    override fun deleteCity(cityId: Int) {
        placesDao.deleteById(cityId)
        weatherDao.deleteById(cityId)
        notifyAboutChanges()
    }

    /**
     * Sets last update time to current time
     * @param cityId city to set time
     */
    override fun setCityUpdateTimeToCurrent(cityId: Int) {
        val place = Place(id = cityId, updated = System.currentTimeMillis())
        placesDao.update(place)
    }


}
