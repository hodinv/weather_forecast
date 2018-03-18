package com.hodinv.weatherforecast.screens.placeslist

import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.mvp.MvpPresenter
import com.hodinv.weatherforecast.mvp.MvpRouter
import com.hodinv.weatherforecast.mvp.MvpView

/**
 * Created by vasily on 17.03.18.
 */
interface PlacesListContract {
    interface View : MvpView {
        fun setPlacesList(places: List<Place>)
        fun setLoading(loading: Boolean)
        fun setShowEmpty(showEmpty: Boolean)
    }

    interface Router : MvpRouter {
        fun showDetail(place: Place)
    }

    interface Presenter : MvpPresenter<View, Router> {
        fun placePressed(place: Place)
        fun refreshData()
    }
}