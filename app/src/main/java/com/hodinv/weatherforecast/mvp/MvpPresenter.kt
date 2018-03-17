package com.hodinv.weatherforecast.mvp

interface MvpPresenter<V : MvpView, R : MvpRouter> {

    var view: V?
    var router: R?

    /**
     * Set view state according to model values
     */
    fun onStart()
    fun onStop()
}

