package com.hodinv.weatherforecast.screens.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.mvp.MvpFragment

/**
 * Created by vasily on 26.03.18.
 */
class ForecastFragment : MvpFragment<ForecastContract.View, ForecastContract.Router, ForecastContract.Presenter>(), ForecastContract.View {


    override fun createPresenter(): ForecastContract.Presenter {
        return ForecastPresenter(arguments?.getInt(KEY_CITY_ID) ?: -1)
    }

    override fun getMvpView(): ForecastContract.View {
        return this
    }

    override fun getRouter(): ForecastContract.Router {
        return activity as ForecastContract.Router
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    companion object {
        val KEY_CITY_ID = "city_id"

        fun getInstance(cityId: Int): ForecastFragment {
            val fragment = ForecastFragment()
            val arguments = Bundle()
            arguments.putInt(KEY_CITY_ID, cityId)
            fragment.arguments = arguments
            return fragment
        }
    }
}