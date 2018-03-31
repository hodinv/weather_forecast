package com.hodinv.weatherforecast.screens.placeslist

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.ForecastItem
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.screens.forecast.ForecastContract

/**
 * Created by vasily on 24.03.18.
 */
class ForecastListAdapter(val presenter: ForecastContract.Presenter) : RecyclerView.Adapter<ForecastListViewHolder>() {
    var items: List<ForecastItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ForecastListViewHolder {
        return ForecastListViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.listitem_forecast, parent, false), presenter)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ForecastListViewHolder?, position: Int) {
        holder?.setItem(items[position])
    }

    fun setForecastItems(places: List<ForecastItem>) {
        items = ArrayList(places)
        notifyDataSetChanged()
    }

}