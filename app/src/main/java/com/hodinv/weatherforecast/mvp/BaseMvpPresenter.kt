package com.hodinv.weatherforecast.mvp

/**
 * Created by vasily on 18.03.18.
 * Base class for Presenter, overrides methods with stubs
 */
abstract class BaseMvpPresenter<V : MvpView, R : MvpRouter> : MvpPresenter<V, R> {
    override var view: V? = null
    override var router: R? = null

    override fun onStart() {
        // stub
    }

    override fun onStop() {
        // stub
    }

    /**
     * TODO: remove it
     */
    override fun onDestroy() {
        // stub
    }
}