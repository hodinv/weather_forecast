package com.hodinv.weatherforecast

import android.Manifest
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.hodinv.weatherforecast.mvp.MvpFragment

import kotlinx.android.synthetic.main.activity_main.*
//import pub.devrel.easypermissions.EasyPermissions
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import com.hodinv.weatherforecast.screens.placeslist.MainActivityFragment
//import pub.devrel.easypermissions.PermissionRequest


class MainActivity : AppCompatActivity()/*, EasyPermissions.PermissionCallbacks*/ {
   /* override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        finish();
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        checkPermissions()
    }*/

    val perms = arrayOf<String>(Manifest.permission.INTERNET)
    val RC_PREMISSIONS = 123;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        checkPermissions()

    }

    private fun checkPermissions() {
       // if (EasyPermissions.hasPermissions(this, *perms)) {
           // startFragment(MainActivityFragment())
       /* } else {
            EasyPermissions.requestPermissions(
                    PermissionRequest.Builder(this, RC_PREMISSIONS, *perms)
                            .setRationale(R.string.permission_rationale)
                            .build())
        }*/
    }


    fun startFragment(newFragment: Fragment) {
        // clear stack
        val count = supportFragmentManager.backStackEntryCount
        for (i in 0..count) {
            supportFragmentManager.popBackStackImmediate();
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        //EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }


}
