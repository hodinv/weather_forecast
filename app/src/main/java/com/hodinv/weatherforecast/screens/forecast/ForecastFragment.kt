package com.hodinv.weatherforecast.screens.forecast

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.ForecastItem
import com.hodinv.weatherforecast.database.DatabaseProvider
import com.hodinv.weatherforecast.mvp.MvpFragment
import com.hodinv.weatherforecast.service.NetworkServiceControllerImpl

/**
 * Created by vasily on 26.03.18.
 */
class ForecastFragment : MvpFragment<ForecastContract.View, ForecastContract.Router, ForecastContract.Presenter>(), ForecastContract.View {
    override fun setLoading(loading: Boolean) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setViewTitle(title: String) {
        activity?.title = title
    }


    override fun setForecastData(items: Array<ForecastItem>) {
        Log.d("ITEMS", "size = ${items.size}")
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun createPresenter(): ForecastContract.Presenter {
        return ForecastPresenter(arguments?.getInt(KEY_CITY_ID) ?: -1,
                NetworkServiceControllerImpl(activity!!),
                DatabaseProvider.instance,
                DatabaseProvider.instance.getWeatherService(),
                DatabaseProvider.instance.getForecastService()
        )
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