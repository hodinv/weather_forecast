package com.hodinv.weatherforecast.screens.forecast

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.ForecastItem
import com.hodinv.weatherforecast.database.DatabaseProvider
import com.hodinv.weatherforecast.mvp.MvpFragment
import com.hodinv.weatherforecast.service.NetworkServiceControllerImpl
import kotlinx.android.synthetic.main.fragment_forecast.*

/**
 * Created by vasily on 26.03.18.
 * Fragment for forecast screen
 */
class ForecastFragment : MvpFragment<ForecastContract.View, ForecastContract.Router, ForecastContract.Presenter>(), ForecastContract.View {

    private var refreshMenu: MenuItem? = null
    private lateinit var adapter: ForecastListAdapter

    /**
     * Set loading state
     * @param loading true of loading is in progress
     */
    override fun setLoading(loading: Boolean) {
        if (!loading) refresh.isRefreshing = false
        refreshMenu?.isVisible = loading
    }

    /**
     * Changes title of screen
     * @param title to set
     */
    override fun setViewTitle(title: String) {
        activity?.title = title
    }

    /**
     * Set forecast items to show in list
     * @param items array of forecast items
     */
    override fun setForecastData(items: Array<ForecastItem>) {
        adapter.setForecastItems(items.toList())
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

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun getRouter(): ForecastContract.Router {
        return activity as ForecastContract.Router
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.setHasFixedSize(true)
        adapter = ForecastListAdapter()
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context)
        refresh.setOnRefreshListener {
            presenter?.refresh()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
        refreshMenu = menu?.findItem(R.id.menu_refreshing)
        refreshMenu?.isVisible = false
    }

    companion object {
        const val KEY_CITY_ID = "city_id"

        /**
         * Creates instance of fragment and set city id in arguments
         * @param cityId city to show in fragment
         */
        fun getInstance(cityId: Int): ForecastFragment {
            val fragment = ForecastFragment()
            val arguments = Bundle()
            arguments.putInt(KEY_CITY_ID, cityId)
            fragment.arguments = arguments
            return fragment
        }
    }
}