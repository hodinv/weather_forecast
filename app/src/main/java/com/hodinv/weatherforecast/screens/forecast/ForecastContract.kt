package com.hodinv.weatherforecast.screens.forecast

import com.hodinv.weatherforecast.data.ForecastItem
import com.hodinv.weatherforecast.mvp.MvpPresenter
import com.hodinv.weatherforecast.mvp.MvpRouter
import com.hodinv.weatherforecast.mvp.MvpView

/**
 * Created by vasily on 17.03.18.
 * Contract for forecast screen
 */
interface ForecastContract {
    interface View : MvpView {
        /**
         * Changes title of screen
         * @param title to set
         */
        fun setViewTitle(title: String)

        /**
         * Set forecast items to show in list
         * @param items array of forecast items
         */
        fun setForecastData(items: Array<ForecastItem>)

        /**
         * Set loading state
         * @param loading true of loading is in progress
         */
        fun setLoading(loading: Boolean)
    }

    interface Router : MvpRouter
    interface Presenter : MvpPresenter<View, Router> {
        /**
         * Refresh forecast data
         */
        fun refresh()
    }
}