package com.hodinv.weatherforecast.screens.forecast

import com.hodinv.weatherforecast.mvp.BaseMvpPresenter

/**
 * Created by vasily on 26.03.18.
 */
class ForecastPresenter(val cityId: Int) : BaseMvpPresenter<ForecastContract.View, ForecastContract.Router>(), ForecastContract.Presenter {

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}