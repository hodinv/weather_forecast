package com.hodinv.weatherforecast.screens.permissions

import com.hodinv.weatherforecast.mvp.MvpPresenter
import com.hodinv.weatherforecast.mvp.MvpRouter
import com.hodinv.weatherforecast.mvp.MvpView

/**
 * Created by vasily on 18.03.18.
 * Contract for permissions checking screen
 */
interface PermissionsContract {
    interface View : MvpView {
        /**
         * Test what permissions are still not granted
         * @return array of not yet granted permissions
         */
        fun getNotGranter(): Array<String>

        /**
         * Perform permissions request
         * @param notGranter array of still not granted permissions
         */
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
        /**
         * Check if permissions granted and react on it
         * @param grantResults array of results (granted/denied)
         */
        fun checkGrants(grantResults: IntArray)
    }
}