package com.hodinv.weatherforecast.screens.permissions

import com.hodinv.weatherforecast.mvp.MvpPresenter
import com.hodinv.weatherforecast.mvp.MvpRouter
import com.hodinv.weatherforecast.mvp.MvpView

/**
 * Created by vasily on 18.03.18.
 */
interface PermissionsContract {
    interface View : MvpView {
        fun getNotGranter(): Array<String>
        fun requestPermissions(notGranter: Array<String>)

    }

    interface Router : MvpRouter {
        /**
         * Opens main view - cities list with weather
         */
        fun startPlacesList()

        /**
         * Exit app
         */
        fun finish()
    }

    interface Presenter : MvpPresenter<View, Router> {
        fun checkGrants(grantResults: IntArray)
    }
}