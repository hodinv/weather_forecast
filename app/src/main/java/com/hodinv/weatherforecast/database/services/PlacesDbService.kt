package com.hodinv.weatherforecast.database.services

import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.database.dao.PlacesDao


/**
 * Created by vasily on 19.03.18.
 */
class PlacesDbService(val placesDao: PlacesDao) : PlacesService {

    override fun getPlaces(): List<Place> {
        return placesDao.getAll()
    }

    override fun addCity(cityId: Int) {
        val place = Place(id = cityId, updated = 0L)
        placesDao.addNew(place)
    }

    override fun deleteCity(cityId: Int) {
        placesDao.deleteById(cityId)
    }

    override fun setCityUpdateTimeToCurrent(cityId: Int) {
        val place = Place(id = cityId, updated = System.currentTimeMillis())
        placesDao.update(place)
    }


}
