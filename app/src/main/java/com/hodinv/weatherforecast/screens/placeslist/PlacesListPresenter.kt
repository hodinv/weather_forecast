package com.hodinv.weatherforecast.screens.placeslist

import android.util.Log
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.services.PlacesService
import com.hodinv.weatherforecast.database.services.WeatherService
import com.hodinv.weatherforecast.database.services.WeatherUpdatesProvider
import com.hodinv.weatherforecast.mvp.BaseMvpPresenter
import com.hodinv.weatherforecast.service.NetworkServiceController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by vasily on 18.03.18.
 */
class PlacesListPresenter(val serviceController: NetworkServiceController,
                          val weatherUpdatesProvider: WeatherUpdatesProvider,
                          val weatherService: WeatherService,
                          val placesService: PlacesService) : BaseMvpPresenter<PlacesListContract.View, PlacesListContract.Router>(), PlacesListContract.Presenter {
    override fun placeLongPressed(place: WeatherInfo) {
        placesService.deleteCity(place.id)
    }

    override fun addCity(name: String) {
        view?.setLoading(true)
        serviceController.waitForControllerReady().subscribe {
            serviceController.searchAndAddNewPlace(name).observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (!it) view?.notAdded()
                    }, {
                        Log.e("Err", it.message)
                        it.printStackTrace()
                        view?.notAdded()
                    })
        }
    }

    override fun placePressed(place: WeatherInfo) {
        router?.showDetail(place.id)
    }

    override fun refreshData() {
        serviceController.requestWeather(true)
    }

    fun onWeatherUpdate() {
        Log.d("DbUpdate", "updateed")
        view?.setPlacesList(weatherService.getWeatherInfo())
    }

    private var updates: Disposable? = null

    private var weatherSubscription: Disposable? = null

    override fun onStart() {
        super.onStart()
        weatherSubscription = weatherUpdatesProvider.getWeatherUpdates().observeOn(AndroidSchedulers.mainThread())
                .subscribe { onWeatherUpdate() }
        view?.setPlacesList(weatherService.getWeatherInfo())
        updates = serviceController.getStateSubscription()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("ServiceUpdate", "=" + serviceController.isWeatherRequestRunning())
                    if (!serviceController.isWeatherRequestRunning()) {
                        view?.setLoading(false)
                    }
                }
        serviceController.waitForControllerReady()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("ServiceReady", "yes")
                    view?.setLoading(true)
                    serviceController.requestWeather(true)
                })
    }

    override fun onStop() {
        super.onStop()
        updates?.dispose()
        updates = null
        weatherSubscription?.dispose()
        weatherSubscription = null
    }
}