package com.hodinv.weatherforecast.database.services

import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.database.dao.PlacesDao
import com.hodinv.weatherforecast.database.dao.WeatherDao


/**
 * Created by vasily on 19.03.18.
 */
class PlacesDbService(private val placesDao: PlacesDao, private val weatherDao: WeatherDao, private val notifyAboutChanges: () -> Unit) : PlacesService {
    override fun hasCity(id: Int): Boolean {
        val items = placesDao.getAll()
        var result = false
        items.forEach { if (it.id == id) result = true }
        return result
    }

    override fun getPlaces(): List<Place> {
        return placesDao.getAll()
    }

    override fun addCity(cityId: Int) {
        val place = Place(id = cityId, updated = 0L)
        placesDao.addNew(place)
    }

    override fun deleteCity(cityId: Int) {
        placesDao.deleteById(cityId)
        weatherDao.deleteById(cityId)
        notifyAboutChanges()
    }

    override fun setCityUpdateTimeToCurrent(cityId: Int) {
        val place = Place(id = cityId, updated = System.currentTimeMillis())
        placesDao.update(place)
    }


}
