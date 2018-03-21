package com.hodinv.weatherforecast.database.services

import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.database.dao.PlacesDao


/**
 * Created by vasily on 19.03.18.
 */
class PlacesService(val placesDao: PlacesDao) {

    fun getPlaces(): List<Place> {
        return placesDao.getAll()
    }

    fun addCity(cityId: Int) {
        val place = Place(id = cityId, updated = 0L)
        placesDao.addNew(place)
    }

    fun deleteCity(cityId: Int) {
        placesDao.deleteById(cityId)
    }

    fun setCityUpdateTimeToCurrent(cityId: Int) {
        val place = Place(id = cityId, updated = System.currentTimeMillis())
        placesDao.update(place)
    }


}
