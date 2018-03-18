package com.hodinv.weatherforecast.screens.placeslist

import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.mvp.BaseMvpPresenter

/**
 * Created by vasily on 18.03.18.
 */
class PlacesListPresenter: BaseMvpPresenter<PlacesListContract.View, PlacesListContract.Router>(), PlacesListContract.Presenter {
    override fun placePressed(place: Place) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}