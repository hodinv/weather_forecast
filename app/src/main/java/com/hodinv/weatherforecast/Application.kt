package com.hodinv.weatherforecast

import android.app.Application
import com.hodinv.weatherforecast.database.DatabaseProvider

/**
 * Created by vasily on 19.03.18.
 */
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseProvider.initialize(this)
    }
}