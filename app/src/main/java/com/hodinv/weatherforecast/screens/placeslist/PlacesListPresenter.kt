package com.hodinv.weatherforecast.screens.placeslist

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
class PlacesListPresenter(private val serviceController: NetworkServiceController,
                          private val weatherUpdatesProvider: WeatherUpdatesProvider,
                          private val weatherService: WeatherService,
                          private val placesService: PlacesService) : BaseMvpPresenter<PlacesListContract.View, PlacesListContract.Router>(), PlacesListContract.Presenter {
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
                        it.printStackTrace()
                        view?.notAdded()
                    })
        }
    }

    override fun placePressed(place: WeatherInfo) {
        router?.showDetail(place.id)
    }

    override fun refreshData() {
        System.gc()
        serviceController.requestWeather(true)
    }

    private fun onWeatherUpdate() {
        view?.setPlacesList(weatherService.getWeatherInfo())
    }

    private var updates: Disposable? = null

    private var weatherSubscription: Disposable? = null

    override fun onStart() {
        super.onStart()
        weatherSubscription = weatherUpdatesProvider.getWeatherUpdates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { onWeatherUpdate() }
        view?.setPlacesList(weatherService.getWeatherInfo())
        updates = serviceController.getStateSubscription()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (!serviceController.isWeatherRequestRunning()) {
                        view?.setLoading(false)
                    }
                }
        serviceController.waitForControllerReady()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (serviceController.requestWeather(false)) {
                        view?.setLoading(true)
                    }
                })
        System.gc()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceController.close()
    }

    override fun onStop() {
        super.onStop()
        updates?.dispose()
        updates = null
        weatherSubscription?.dispose()
        weatherSubscription = null
    }
}