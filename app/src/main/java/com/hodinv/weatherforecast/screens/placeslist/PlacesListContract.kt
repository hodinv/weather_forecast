package com.hodinv.weatherforecast.screens.placeslist

import com.hodinv.weatherforecast.data.Place

/**
 * Created by vasily on 17.03.18.
 */
interface PlacesListContract {
    interface View {
        fun setPlacesList(places: List<Place>)
        fun setLoading(loading: Boolean)
        fun setShowEmpty(showEmpty: Boolean)
    }

    interface Router {
        fun showDetail(place:Place)
    }

    interface Presenter {
        fun placePressed(place: Place)
        fun refreshData()
    }
}