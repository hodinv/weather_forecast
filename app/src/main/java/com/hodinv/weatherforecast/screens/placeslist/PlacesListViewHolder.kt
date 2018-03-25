package com.hodinv.weatherforecast.screens.placeslist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.data.WeatherInfo

/**
 * Created by vasily on 24.03.18.
 */
class PlacesListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun setItem(place: WeatherInfo) {
        view.findViewById<TextView>(R.id.txt_city).setText(place.name)
    }
}