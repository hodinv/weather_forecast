package com.hodinv.weatherforecast.screens.permissions

import android.content.pm.PackageManager
import com.hodinv.weatherforecast.mvp.BaseMvpPresenter

/**
 * Created by vasily on 18.03.18.
 * Presenter for checking permissions
 */
class PermissionsPresenter : BaseMvpPresenter<PermissionsContract.View, PermissionsContract.Router>(), PermissionsContract.Presenter {

    /**
     * Check if permissions granted and react on it
     * @param grantResults array of results (granted/denied)
     */
    override fun checkGrants(grantResults: IntArray) {
        if (grantResults.any { it != PackageManager.PERMISSION_GRANTED }) {
            router?.finish()
        } else {
            checkPermissions()
        }
    }


    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    private fun checkPermissions() {
        val notGranter = view?.getNotGranter()
        if (notGranter!!.isNotEmpty()) {
            view?.requestPermissions(notGranter)
        } else {
            router?.startPlacesList()
        }
    }
}