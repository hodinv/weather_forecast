package com.hodinv.weatherforecast.mvp

import android.os.Bundle
import android.support.v4.app.Fragment
import com.hodinv.weatherforecast.R


/**
 * Created by vasily
 * Base class from Fragment
 * Defines some abstract methods and links it to fragmetn lifecycle
 */
abstract class MvpFragment<V : MvpView, Router : MvpRouter, P : MvpPresenter<V, Router>> : Fragment() {

    var presenter: P? = null

    /**
     * Create new presenter
     * @return presenter for this fragment
     */
    abstract fun createPresenter(): P

    /**
     * Return view for this fragment, usually - fragment itsef
     * @return View
     */
    abstract fun getMvpView(): V

    /**
     * Return router for this fragment, usually - activity that holds this fragment
     */
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



