package com.hodinv.weatherforecast.screens.placeslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.WeatherInfo

/**
 * Created by vasily on 24.03.18.
 * Adapter for cities weather list
 */
class PlacesListAdapter(val presenter: PlacesListContract.Presenter) : RecyclerView.Adapter<PlacesListViewHolder>() {
    private var items: List<WeatherInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlacesListViewHolder {
        return PlacesListViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.listitem_place, parent, false), presenter)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlacesListViewHolder?, position: Int) {
        holder?.setItem(items[position])
    }

    /**
     * Replace weather data with new one and refresh list
     * @param places list of weather data items
     */
    fun setPlaces(places: List<WeatherInfo>) {
        items = ArrayList(places)
        notifyDataSetChanged()
    }

}