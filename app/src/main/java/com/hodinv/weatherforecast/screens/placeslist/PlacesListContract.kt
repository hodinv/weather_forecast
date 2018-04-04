package com.hodinv.weatherforecast.screens.placeslist

import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.mvp.MvpPresenter
import com.hodinv.weatherforecast.mvp.MvpRouter
import com.hodinv.weatherforecast.mvp.MvpView

/**
 * Created by vasily on 17.03.18.
 * Contract for places list screen
 */
interface PlacesListContract {
    interface View : MvpView {
        /**
         * Sets list of places that should be shown in list
         * @param places list of places
         */
        fun setPlacesList(places: List<WeatherInfo>)

        /**
         * Set loading state
         * @param loading true of loading is in progress
         */
        fun setLoading(loading: Boolean)

        /**
         * Notify user that city was not added to list
         */
        fun notAdded()
    }

    interface Router : MvpRouter {
        /**
         * Opens forecast for city
         * @param cityId city to show forecast for
         */
        fun showDetail(cityId: Int)
    }

    interface Presenter : MvpPresenter<View, Router> {
        /**
         * process  press in city item (show forecast)
         * @param place that was clicked
         */
        fun placePressed(place: WeatherInfo)

        /**
         * Process long press on city item (delete item)
         * @param place that was long pressed
         */
        fun placeLongPressed(place: WeatherInfo)

        /**
         * Force update of data
         */
        fun refreshData()

        /**
         * Try add city by name
         * @param name city name to serach and add
         */
        fun addCity(name: String)
    }
}