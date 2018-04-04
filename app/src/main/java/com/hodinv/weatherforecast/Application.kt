package com.hodinv.weatherforecast

import android.app.Application
import com.hodinv.weatherforecast.database.DatabaseProvider

/**
 * Created by vasily on 19.03.18.
 * Application object - used to initiate singletons
 */
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseProvider.initialize(this)
    }
}