package com.hodinv.weatherforecast.mvp

/**
 * Created by vasily
 * Base interface for presenter
 */
interface MvpPresenter<V : MvpView, R : MvpRouter> {

    /**
     * link to View
     */
    var view: V?
    /**
     * link to Rounter
     */
    var router: R?

    /**
     * Set view state according to model values
     */
    fun onStart()

    /**
     * Stops callbacks for view changes, receiver etc.
     */
    fun onStop()


    /**
     * Stops/destoys general sevvices used by presenter
     */
    fun onDestroy()
}

