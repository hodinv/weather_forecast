package com.hodinv.weatherforecast.screens.placeslist

import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.repository.PlacesRepository
import com.hodinv.weatherforecast.database.repository.RepositoryUpdatesProvider
import com.hodinv.weatherforecast.database.repository.WeatherRepository
import com.hodinv.weatherforecast.mvp.BaseMvpPresenter
import com.hodinv.weatherforecast.service.NetworkServiceController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by vasily on 18.03.18.
 * Presenter for places list screen
 */
class PlacesListPresenter(private val serviceController: NetworkServiceController,
                          private val weatherUpdatesProvider: RepositoryUpdatesProvider,
                          private val weatherService: WeatherRepository,
                          private val placesService: PlacesRepository) : BaseMvpPresenter<PlacesListContract.View, PlacesListContract.Router>(), PlacesListContract.Presenter {
    /**
     * Process long press on city item (delete item)
     * @param place that was long pressed
     */
    override fun placeLongPressed(place: WeatherInfo) {
        placesService.deleteCity(place.id)
    }

    /**
     * Try add city by name
     * @param name city name to search and add
     */
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

    /**
     * process  press in city item (show forecast)
     * @param place that was clicked
     */
    override fun placePressed(place: WeatherInfo) {
        router?.showDetail(place.id)
    }

    /**
     * Force update of data
     */
    override fun refreshData() {
        serviceController.requestWeather(true)
    }

    /**
     * React on weather model update
     */
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
    }

    override fun onStop() {
        super.onStop()
        updates?.dispose()
        updates = null
        weatherSubscription?.dispose()
        weatherSubscription = null
    }
}