package com.hodinv.weatherforecast.screens.placeslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.data.WeatherInfo

/**
 * Created by vasily on 24.03.18.
 */
class PlacesListAdapter : RecyclerView.Adapter<PlacesListViewHolder>() {
    var items: List<WeatherInfo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PlacesListViewHolder {
        return PlacesListViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.listitem_place, null, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: PlacesListViewHolder?, position: Int) {
        holder?.setItem(items[position])
    }

    fun setPlaces(places: List<WeatherInfo>) {
        if (items.isEmpty()) {
            items = ArrayList(places)
            notifyDataSetChanged()
        } else {
            //todo:  update and insert + delete
        }

    }

}