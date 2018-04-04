package com.hodinv.weatherforecast

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.hodinv.weatherforecast.screens.forecast.ForecastContract
import com.hodinv.weatherforecast.screens.forecast.ForecastFragment
import com.hodinv.weatherforecast.screens.permissions.PermissionsContract
import com.hodinv.weatherforecast.screens.permissions.PermissionsFragment
import com.hodinv.weatherforecast.screens.placeslist.PlacesListContract
import com.hodinv.weatherforecast.screens.placeslist.PlacesListFragment
import com.hodinv.weatherforecast.utils.SettingsHelper
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by vasily
 * Main activity. Holds all fragments.
 * Implements routers functionality. Also handles last selected forecast and opens it on restart
 */
class MainActivity : AppCompatActivity(),
        PermissionsContract.Router,
        PlacesListContract.Router,
        ForecastContract.Router {

    /**
     * Opens forecast for city
     * @param cityId city to show forecast for
     */
    override fun showDetail(cityId: Int) {
        startFragmentWithStacking(ForecastFragment.getInstance(cityId))
        SettingsHelper(this).setCityId(cityId)
    }

    /**
     * Opens main view - cities list with weather
     */
    override fun startPlacesList() {
        startFragment(PlacesListFragment())
    }

    override fun onBackPressed() {
        super.onBackPressed()
        SettingsHelper(this).clearCity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (SettingsHelper(this).getCity() != SettingsHelper.NO_CITY) {
            startPlacesList()
            showDetail(SettingsHelper(this).getCity())
        } else {
            startFragment(PermissionsFragment())
        }
    }


    /**
     * Replace current content fragment with new one
     * @param newFragment fragment to show
     */
    private fun startFragment(newFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newFragment)

        transaction.commit()
    }

    /**
     * Adds current fragment ot back stack and shows new fragment
     * @param newFragment fragment to show
     */
    private fun startFragmentWithStacking(newFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newFragment).addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
