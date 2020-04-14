package com.example.covid19_tracker

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.covid19_tracker.model.Country
import com.example.covid19_tracker.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private val FIRST_RUN = "first_run_flag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*Mostafa Start*/
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        /*Mostafa End*/

        /*AboElnaga Start*/

        /*AboElnaga End*/
    }

    override fun onStart() {
        super.onStart()

        if (checkFirstRun()) {
            if (checkConnectivity()) {
                viewModel.getNewData().observe(this, Observer<List<Country>> { countries ->
                    // update UI
                    if (countries != null) {
                        val pref = getPreferences(Context.MODE_PRIVATE)
                        val editor = pref.edit()
                        editor.putBoolean(FIRST_RUN, true)
                        editor.apply()
                        for (country in countries) {
                            println(country.country_name)
                        }
                    } else {
                        Toast.makeText(this, "Error Fetching Data", Toast.LENGTH_LONG).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_LONG).show()
            }
        } else {
            viewModel.getSavedData().observe(this, Observer<List<Country>> { countries ->
                // update UI
                if (countries != null) {
                    for (country in countries) {
                        println(country.country_name)
                    }
                } else {
                    Toast.makeText(this, "Please Check Internet Connection", Toast.LENGTH_LONG).show()
                }

            })
        }

    }

    private fun checkConnectivity(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (!isConnected) {
            Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()
        }
        return isConnected
    }

    private fun checkFirstRun(): Boolean {
        val pref = getPreferences(Context.MODE_PRIVATE)
        val isFirstRun = pref.getBoolean(FIRST_RUN, false)
        return isFirstRun
    }

    /*Mostafa End*/

    /*AboElnaga Start*/

    /*AboElnaga End*/
}
