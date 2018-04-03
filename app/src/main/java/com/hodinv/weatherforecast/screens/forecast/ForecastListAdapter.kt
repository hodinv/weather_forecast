package com.hodinv.weatherforecast.screens.forecast

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.ForecastItem

/**
 * Created by vasily on 24.03.18.
 */
class ForecastListAdapter() : RecyclerView.Adapter<ForecastListViewHolder>() {
    private var items: List<ForecastItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ForecastListViewHolder {
        return ForecastListViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.listitem_forecast, parent, false))
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