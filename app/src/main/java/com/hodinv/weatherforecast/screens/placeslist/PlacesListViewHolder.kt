package com.hodinv.weatherforecast.screens.placeslist

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.utils.TempHelper

/**
 * Created by vasily on 24.03.18.
 * View holder for one of cities weather items
 */
class PlacesListViewHolder(val view: View, presenter: PlacesListContract.Presenter) : RecyclerView.ViewHolder(view) {
    private val city: TextView = view.findViewById(R.id.txt_city)
    private val temp: TextView = view.findViewById(R.id.txt_temp)
    private val tempRange: TextView = view.findViewById(R.id.txt_temp_range)
    private val weather: TextView = view.findViewById(R.id.txt_weather)
    private var place: WeatherInfo? = null

    init {
        view.findViewById<CardView>(R.id.card).setOnClickListener { if (place != null) presenter.placePressed(place!!) }
        view.findViewById<CardView>(R.id.card).setOnLongClickListener {
            if (place != null) presenter.placeLongPressed(place!!)
            true
        }
    }


    fun setItem(place: WeatherInfo) {
        this.place = place
        city.text = place.name
        temp.text = view.context.getString(R.string.format_temp).format(TempHelper.kelvinToCelsuis(place.main.temp))
        if (Math.abs(place.main.tempMax - place.main.temp) > 0.09 ||
                Math.abs(place.main.tempMin - place.main.temp) > 0.09) {
            tempRange.text = view.context.getString(R.string.format_temp_range).format(
                    TempHelper.kelvinToCelsuis(place.main.tempMin),
                    TempHelper.kelvinToCelsuis(place.main.tempMax)
            )
        } else {
            tempRange.text = "" // hide if min==max==main
        }
        weather.text = place.weatherInfo
    }
}