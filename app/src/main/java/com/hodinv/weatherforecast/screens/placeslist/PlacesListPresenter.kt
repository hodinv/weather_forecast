package com.hodinv.weatherforecast.screens.placeslist

import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.mvp.BaseMvpPresenter
import com.hodinv.weatherforecast.service.NetworkServiceController

/**
 * Created by vasily on 18.03.18.
 */
class PlacesListPresenter(val serviceController: NetworkServiceController) : BaseMvpPresenter<PlacesListContract.View, PlacesListContract.Router>(), PlacesListContract.Presenter {
    override fun placePressed(place: Place) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStart() {
        super.onStart()
        serviceController.requestWeather()
    }
}