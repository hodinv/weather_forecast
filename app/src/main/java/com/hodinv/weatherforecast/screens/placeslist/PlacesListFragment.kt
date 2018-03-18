package com.hodinv.weatherforecast.screens.placeslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.mvp.MvpFragment
import com.hodinv.weatherforecast.service.NetworkServiceControllerImpl

/**
 * A placeholder fragment containing a simple view.
 */
class PlacesListFragment : MvpFragment<PlacesListContract.View, PlacesListContract.Router, PlacesListContract.Presenter>(), PlacesListContract.View {
    override fun setPlacesList(places: List<Place>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setLoading(loading: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setShowEmpty(showEmpty: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createPresenter(): PlacesListContract.Presenter {
        return PlacesListPresenter(NetworkServiceControllerImpl(activity))
    }

    override fun getMvpView(): PlacesListContract.View {
        return this
    }

    override fun getRouter(): PlacesListContract.Router {
        return activity as PlacesListContract.Router
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_places_list, container, false)
    }
}
