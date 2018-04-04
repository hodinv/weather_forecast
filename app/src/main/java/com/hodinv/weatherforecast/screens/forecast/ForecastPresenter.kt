package com.hodinv.weatherforecast.screens.forecast

import com.hodinv.weatherforecast.database.repository.ForecastRepository
import com.hodinv.weatherforecast.database.repository.WeatherRepository
import com.hodinv.weatherforecast.database.repository.RepositoryUpdatesProvider
import com.hodinv.weatherforecast.mvp.BaseMvpPresenter
import com.hodinv.weatherforecast.service.NetworkServiceController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by vasily on 26.03.18.
 */
class ForecastPresenter(val cityId: Int,
                        private val serviceController: NetworkServiceController,
                        private val weatherUpdatesProvider: RepositoryUpdatesProvider,
                        private val weatherService: WeatherRepository,
                        private val forecastService: ForecastRepository
) : BaseMvpPresenter<ForecastContract.View, ForecastContract.Router>(), ForecastContract.Presenter {
    override fun refresh() {
        serviceController.requestForecast(cityId, true)
    }

    private var forecastSubscription: Disposable? = null
    private var updates: Disposable? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceController.close()
    }

    override fun onStart() {
        super.onStart()
        forecastSubscription = weatherUpdatesProvider.getForecastUpdate()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it == cityId) {
                        onForecastUpdate()
                    }
                }
        val forecast = forecastService.getForecast(cityId)
        val weather = weatherService.getWeatherInfo(cityId)
        view?.setForecastData(forecast.forecast.list)
        view?.setViewTitle(weather.name)
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