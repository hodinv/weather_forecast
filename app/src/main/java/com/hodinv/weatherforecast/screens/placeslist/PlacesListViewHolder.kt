package com.hodinv.weatherforecast.screens.placeslist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.utils.TempHelper

/**
 * Created by vasily on 24.03.18.
 */
class PlacesListViewHolder(val view: View, presenter: PlacesListContract.Presenter) : RecyclerView.ViewHolder(view) {
    private val city: TextView = view.findViewById(R.id.txt_city);
    private val temp: TextView = view.findViewById(R.id.txt_temp);
    private val tempRange: TextView = view.findViewById(R.id.txt_temp_range);
    private val wind: TextView = view.findViewById(R.id.txt_wind)
    private var place: WeatherInfo? = null

    init {
        view.setOnClickListener { if (place != null) presenter.placePressed(place!!) }
    }


    fun setItem(place: WeatherInfo) {
        this.place = place
        city.text = place.name
        // todo: extract to strings
        temp.text = "%+.1f°C".format(TempHelper.kelvinToCelsuis(place.main.temp))
        if (Math.abs(place.main.temp_max - place.main.temp) > 0.09 ||
                Math.abs(place.main.temp_min - place.main.temp) > 0.09) {
            tempRange.text = "%+.1f°C...%+.1f°C".format(
                    TempHelper.kelvinToCelsuis(place.main.temp_min),
                    TempHelper.kelvinToCelsuis(place.main.temp_max)
            )
        } else {
            tempRange.text = "" // hide if min==max==main
        }
        wind.text = "%+.2f m/s".format(place.wind.speed)
    }
}