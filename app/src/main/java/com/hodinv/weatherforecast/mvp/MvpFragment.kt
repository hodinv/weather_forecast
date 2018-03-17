package com.hodinv.weatherforecast.mvp

import android.support.v4.app.Fragment


abstract class MvpFragment<V : MvpView, R : MvpRouter, P : MvpPresenter<V, R>> : Fragment() {

    var presenter: P? = null

    abstract fun createPresenter(): P
    abstract fun getMvpView(): V
    abstract fun getRouter(): R


    override fun onStart() {
        super.onStart()
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



