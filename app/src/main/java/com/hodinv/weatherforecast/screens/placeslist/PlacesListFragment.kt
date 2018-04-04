package com.hodinv.weatherforecast.screens.placeslist

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.*
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.database.DatabaseProvider
import com.hodinv.weatherforecast.mvp.MvpFragment
import com.hodinv.weatherforecast.service.NetworkServiceController
import com.hodinv.weatherforecast.service.NetworkServiceControllerImpl
import kotlinx.android.synthetic.main.fragment_places_list.*


/**
 * Created by vasily
 * Fragment for places list screen
 */
class PlacesListFragment : MvpFragment<PlacesListContract.View, PlacesListContract.Router, PlacesListContract.Presenter>(), PlacesListContract.View {
    /**
     * Notify user that city was not added to list
     */
    override fun notAdded() {
        if (activity != null) {
            Toast.makeText(activity!!, "Not Added", Toast.LENGTH_SHORT).show()
        }
    }

    private var refreshMenu: MenuItem? = null
    private lateinit var adapter: PlacesListAdapter

    /**
     * Sets list of places that should be shown in list
     * @param places list of places
     */
    override fun setPlacesList(places: List<WeatherInfo>) {
        adapter.setPlaces(places)
    }

    /**
     * Set loading state
     * @param loading true of loading is in progress
     */
    override fun setLoading(loading: Boolean) {
        if (!loading) refresh.isRefreshing = false
        refreshMenu?.isVisible = loading
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceController?.close()
        serviceController = null
    }

    var serviceController: NetworkServiceController? = null
    override fun createPresenter(): PlacesListContract.Presenter {
        serviceController?.close()
        serviceController = NetworkServiceControllerImpl(activity!!)
        return PlacesListPresenter(NetworkServiceControllerImpl(activity!!),
                DatabaseProvider.instance,
                DatabaseProvider.instance.getWeatherService(),
                DatabaseProvider.instance.getPlacesService())
    }

    override fun getMvpView(): PlacesListContract.View {
        return this
    }

    override fun getRouter(): PlacesListContract.Router {
        return activity as PlacesListContract.Router
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.setHasFixedSize(true)
        adapter = PlacesListAdapter(presenter!!)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(context)
        btn_new.setOnClickListener {
            MaterialDialog.Builder(activity!!)
                    .title(R.string.new_city_title)
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("", "",
                            { _, input ->
                                // do stuff here
                                presenter?.addCity(input.toString())
                            }).show()
        }
        refresh.setOnRefreshListener {
            presenter?.refreshData()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
        refreshMenu = menu?.findItem(R.id.menu_refreshing)
        refreshMenu?.isVisible = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_places_list, container, false)
    }
}
