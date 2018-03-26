package com.hodinv.weatherforecast.screens.placeslist

import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.mvp.MvpPresenter
import com.hodinv.weatherforecast.mvp.MvpRouter
import com.hodinv.weatherforecast.mvp.MvpView

/**
 * Created by vasily on 17.03.18.
 */
interface PlacesListContract {
    interface View : MvpView {
        fun setPlacesList(places: List<WeatherInfo>)
        fun setLoading(loading: Boolean)
        fun notAdded()
    }

    interface Router : MvpRouter {
        fun showDetail(cityId: Int)
    }

    interface Presenter : MvpPresenter<View, Router> {
        fun placePressed(place: WeatherInfo)
        fun placeLongPressed(place: WeatherInfo)
        fun refreshData()
        fun addCity(name: String)
    }
}