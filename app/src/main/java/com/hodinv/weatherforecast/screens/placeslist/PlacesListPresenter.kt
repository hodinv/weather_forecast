package com.hodinv.weatherforecast.screens.placeslist

import android.util.Log
import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.services.WeatherService
import com.hodinv.weatherforecast.database.services.WeatherUpdatesProvider
import com.hodinv.weatherforecast.mvp.BaseMvpPresenter
import com.hodinv.weatherforecast.service.NetworkServiceController
import io.reactivex.disposables.Disposable

/**
 * Created by vasily on 18.03.18.
 */
class PlacesListPresenter(val serviceController: NetworkServiceController, val weatherUpdatesProvider: WeatherUpdatesProvider, val weatherService: WeatherService) : BaseMvpPresenter<PlacesListContract.View, PlacesListContract.Router>(), PlacesListContract.Presenter {
    override fun placePressed(place: WeatherInfo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun refreshData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onWeatherUpdate() {
        Log.d("DbUpdate", "updateed")
        val items = weatherService.getWeatherInfo()
        Log.d("DbUpdate", "size=" + items.size)
        view?.setPlacesList(items)//weatherService.getWeatherInfo())
    }

    private var updates: Disposable? = null

    override fun onStart() {
        super.onStart()
        weatherUpdatesProvider.addWeatherListener(::onWeatherUpdate)
        view?.setPlacesList(weatherService.getWeatherInfo())
        updates = serviceController.getStateSubscription().subscribe {
            Log.d("ServiceUpdate", "=" + serviceController.isWeatherRequestRunning())
        }
        serviceController.waitForControllerReady().subscribe({
            Log.d("ServiceReady", "yes")
            serviceController.requestWeather(true)
        })
    }

    override fun onStop() {
        super.onStop()
        weatherUpdatesProvider.removeWeatherListener(::onWeatherUpdate)
        updates?.dispose()
        updates = null
    }
}