package com.hodinv.weatherforecast.screens.forecast

import com.hodinv.weatherforecast.data.ForecastItem
import com.hodinv.weatherforecast.mvp.MvpPresenter
import com.hodinv.weatherforecast.mvp.MvpRouter
import com.hodinv.weatherforecast.mvp.MvpView

/**
 * Created by vasily on 17.03.18.
 */
interface ForecastContract {
    interface View : MvpView {
        fun setViewTitle(title: String)
        fun setForecastData(items: Array<ForecastItem>)
        fun setLoading(loading: Boolean)
    }

    interface Router : MvpRouter
    interface Presenter : MvpPresenter<View, Router> {
        fun refresh()
    }
}