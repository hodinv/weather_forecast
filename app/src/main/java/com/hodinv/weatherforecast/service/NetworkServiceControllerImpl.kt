package com.hodinv.weatherforecast.service

import android.content.Context
import android.content.Intent

/**
 * Created by vasily on 18.03.18.
 * Process usecases for sending proper request to service. Including force reqfresh logic
 */
class NetworkServiceControllerImpl(val context: Context) : NetworkServiceController {
    override fun requestWeather() {
        val intent = Intent(NetworkRequestsPerformer.GET_WEATHR_ACTION)
        intent.setPackage("com.hodinv.weatherforecast")
        context.startService(intent)
    }
}