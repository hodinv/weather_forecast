package com.hodinv.weatherforecast.screens.forecast

import android.util.Log
import com.hodinv.weatherforecast.database.services.ForecastService
import com.hodinv.weatherforecast.database.services.WeatherService
import com.hodinv.weatherforecast.database.services.WeatherUpdatesProvider
import com.hodinv.weatherforecast.mvp.BaseMvpPresenter
import com.hodinv.weatherforecast.service.NetworkServiceController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by vasily on 26.03.18.
 */
class ForecastPresenter(val cityId: Int,
                        val serviceController: NetworkServiceController,
                        val weatherUpdatesProvider: WeatherUpdatesProvider,
                        val weatherService: WeatherService,
                        val forecastService: ForecastService
) : BaseMvpPresenter<ForecastContract.View, ForecastContract.Router>(), ForecastContract.Presenter {
    override fun refresh() {
        serviceController.requestForecast(cityId, true)
    }

    private var forecastSubscription: Disposable? = null
    private var updates: Disposable? = null

    override fun onStart() {
        super.onStart()
        forecastSubscription = weatherUpdatesProvider.getForecastUpdate(cityId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onForecastUpdate() }
        val forecast = forecastService.getForecast(cityId)
        view?.setForecastData(forecast.forecast.list)
        view?.setViewTitle(forecast.forecast.city.name)
        updates = serviceController.getStateSubscription()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (!serviceController.isForecastRequestRunning(cityId)) {
                        view?.setLoading(false)
                    }
                }
        serviceController.waitForControllerReady()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("ServiceReady", "yes")
                    if (serviceController.requestForecast(cityId, true)) {
                        view?.setLoading(true)
                    }
                })
    }

    private fun onForecastUpdate() {
        view?.setForecastData(forecastService.getForecast(cityId).forecast.list)
    }

    override fun onStop() {
        super.onStop()
        updates?.dispose()
        updates = null
        forecastSubscription?.dispose()
        forecastSubscription = null
    }
}