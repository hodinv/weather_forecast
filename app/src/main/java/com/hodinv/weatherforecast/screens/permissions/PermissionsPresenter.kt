package com.hodinv.weatherforecast.screens.permissions

import android.content.pm.PackageManager
import com.hodinv.weatherforecast.mvp.BaseMvpPresenter

/**
 * Created by vasily on 18.03.18.
 */
class PermissionsPresenter : BaseMvpPresenter<PermissionsContract.View, PermissionsContract.Router>(), PermissionsContract.Presenter {
    override fun checkGrants(grantResults: IntArray) {
        if (grantResults.filter { it != PackageManager.PERMISSION_GRANTED }.isNotEmpty()) {
            // todo: say why
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