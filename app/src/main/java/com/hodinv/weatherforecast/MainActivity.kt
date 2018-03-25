package com.hodinv.weatherforecast

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.hodinv.weatherforecast.data.Place
import com.hodinv.weatherforecast.screens.permissions.PermissionsContract
import com.hodinv.weatherforecast.screens.permissions.PermissionsFragment
import com.hodinv.weatherforecast.screens.placeslist.PlacesListContract
import com.hodinv.weatherforecast.screens.placeslist.PlacesListFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(),
        PermissionsContract.Router,
        PlacesListContract.Router {

    override fun showDetail(cityId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startPlacesList() {
        startFragment(PlacesListFragment())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        startFragment(PermissionsFragment())
    }


    fun startFragment(newFragment: Fragment) {
        // clear stack

        val count = supportFragmentManager.backStackEntryCount
        for (i in 0..count) {
            //supportFragmentManager.popBackStackImmediate();
        }

        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newFragment);//.addToBackStack(null)

        transaction.commit()
    }

    fun startFragmentWithStacking(newFragment: Fragment) {
        var transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newFragment).addToBackStack(null)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

}
