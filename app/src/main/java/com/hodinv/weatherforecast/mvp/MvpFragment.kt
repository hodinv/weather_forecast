package com.hodinv.weatherforecast.mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import com.hodinv.weatherforecast.R


abstract class MvpFragment<V : MvpView, Router : MvpRouter, P : MvpPresenter<V, Router>> : Fragment() {

    var presenter: P? = null

    abstract fun createPresenter(): P
    abstract fun getMvpView(): V
    abstract fun getRouter(): Router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onStart() {
        super.onStart()
        activity?.title = getString(R.string.app_name)
        presenter?.view = getMvpView()
        presenter?.router = getRouter()
        presenter?.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter?.onStop()
        presenter?.view = null
        presenter?.router = null
    }
}



