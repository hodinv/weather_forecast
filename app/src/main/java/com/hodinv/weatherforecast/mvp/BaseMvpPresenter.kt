package com.hodinv.weatherforecast.mvp

/**
 * Created by vasily on 18.03.18.
 */
abstract class BaseMvpPresenter<V : MvpView, R : MvpRouter> : MvpPresenter<V, R> {
    override var view: V? = null
    override var router: R? = null

    override fun onStart() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }
}