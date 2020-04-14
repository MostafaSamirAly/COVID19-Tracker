package com.example.covid19_tracker

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.covid19_tracker.db.CountryDataBase
import com.example.covid19_tracker.model.Country
import com.example.covid19_tracker.repository.CountryRepositoryImp
import com.example.covid19_tracker.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel : MainViewModel


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
        viewModel.getNewData().observe(this, Observer<List<Country>> { countries ->
            // update UI
//            if(countries != null){
//                for (country in countries) {
//                    println(country.country_name)
//                }
//            }else{
//                println("Error Fetching Data")
//            }

        })
    }
    /*Mostafa End*/

    /*AboElnaga Start*/

    /*AboElnaga End*/
}
