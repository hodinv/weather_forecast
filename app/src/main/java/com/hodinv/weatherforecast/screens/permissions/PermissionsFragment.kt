package com.hodinv.weatherforecast.screens.permissions

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.PermissionChecker
import com.hodinv.weatherforecast.mvp.MvpFragment

/**
 * Created by vasily on 18.03.18.
 */
class PermissionsFragment : MvpFragment<PermissionsContract.View, PermissionsContract.Router, PermissionsContract.Presenter>(), PermissionsContract.View {


    val PERMISSIONS = arrayOf<String>(Manifest.permission.INTERNET)
    val PERMISSIONS_REQ = 123;


    override fun getNotGranter(): Array<String> {
        return PERMISSIONS.filter { PermissionChecker.checkSelfPermission(activity!!, it) != PackageManager.PERMISSION_GRANTED }.toTypedArray()

    }

    override fun createPresenter(): PermissionsContract.Presenter {
        return PermissionsPresenter();
    }

    override fun getMvpView(): PermissionsContract.View {
        return this;
    }

    override fun getRouter(): PermissionsContract.Router {
        return activity as PermissionsContract.Router;
    }

    override fun requestPermissions(notGranter: Array<String>) {
        ActivityCompat.requestPermissions(activity!!, notGranter, PERMISSIONS_REQ);
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        presenter?.checkGrants(grantResults)
    }
}