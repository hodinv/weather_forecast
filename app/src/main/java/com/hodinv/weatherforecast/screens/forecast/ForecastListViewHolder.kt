package com.hodinv.weatherforecast.screens.placeslist

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.hodinv.weatherforecast.R
import com.hodinv.weatherforecast.data.ForecastItem
import com.hodinv.weatherforecast.data.WeatherInfo
import com.hodinv.weatherforecast.screens.forecast.ForecastContract
import com.hodinv.weatherforecast.utils.TempHelper
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by vasily on 24.03.18.
 */
class ForecastListViewHolder(val view: View, presenter: ForecastContract.Presenter) : RecyclerView.ViewHolder(view) {
    private val dateAndTime: TextView = view.findViewById(R.id.txt_date_time);
    private val temp: TextView = view.findViewById(R.id.txt_temp);
    private val tempRange: TextView = view.findViewById(R.id.txt_temp_range);
    private val weather: TextView = view.findViewById(R.id.txt_weather)
    private var forecast: ForecastItem? = null


    fun setItem(forecast: ForecastItem) {
        this.forecast = forecast
        var dateText = FORMAT.format(Date(forecast.dt * 1000))

        if (dateText.startsWith(TODAY_CHECK.format(Date()))) {
            dateText = view.context.getString(R.string.today) + " " + TODAY.format(forecast.dt * 1000)
        }
        // todo: extract to strings
        dateAndTime.text = dateText
        temp.text = "%+.1f°C".format(TempHelper.kelvinToCelsuis(forecast.main.temp))
        if (Math.abs(forecast.main.temp_max - forecast.main.temp) > 0.09 ||
                Math.abs(forecast.main.temp_min - forecast.main.temp) > 0.09) {
            tempRange.text = "%+.1f°C...%+.1f°C".format(
                    TempHelper.kelvinToCelsuis(forecast.main.temp_min),
                    TempHelper.kelvinToCelsuis(forecast.main.temp_max)
            )
        } else {
            tempRange.text = "" // hide if min==max==main
        }
        weather.text = forecast.getWeatherInfo()
    }

    companion object {
        val FORMAT = SimpleDateFormat("dd MMM HH:mm")
        val TODAY = SimpleDateFormat("HH:mm")
        val TODAY_CHECK = SimpleDateFormat("dd MMM")
    }
}