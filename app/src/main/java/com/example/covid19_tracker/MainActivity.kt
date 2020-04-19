package com.example.covid19_tracker

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.example.covid19_tracker.model.Country
import com.example.covid19_tracker.model.WorldState
import com.example.covid19_tracker.repository.CountryRepositoryImp
import com.example.covid19_tracker.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private var activeFragment = Fragment()

    private val homeFragment by lazy { HomeFragment() }
    private val settingsFragment by lazy { SettingsFragment() }
    private val FIRST_RUN = "first_run_flag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*Mostafa Start*/
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setBackGroundSync()
        /*Mostafa End*/

        /*AboElnaga Start*/
        init()
        /*AboElnaga End*/
    }

    override fun onStart() {
        super.onStart()

            if(checkConnectivity()){
                getNewData()
                getNewWorldRecords()
            }else{
                if (checkFirstRun()){
                    Toast.makeText(this,"Internet Connection is a must in first time , restart app",Toast.LENGTH_LONG).show()
                }else{
                    getSavedData()
                    getWorldRecordsSavedData()
                    Toast.makeText(this, "Check network connection", Toast.LENGTH_SHORT).show()
                }
            }


    }

    private fun setBackGroundSync() {

        //create constraints to attach it to the request
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        //create the request
        val myRequest = PeriodicWorkRequestBuilder<MyWorker>(repeatInterval = 1 , repeatIntervalTimeUnit = TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("update",ExistingPeriodicWorkPolicy.KEEP,myRequest)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myRequest.id)
            .observe(this, Observer { workInfo ->
                if(workInfo.state == WorkInfo.State.SUCCEEDED){
                    viewModel.getNewData()
                    viewModel.getNewWorldData()
                }

        })
    }

    private fun checkConnectivity(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        return isConnected
    }

    private fun checkFirstRun(): Boolean {
        val pref = getPreferences(Context.MODE_PRIVATE)
        val isFirstRun = pref.getBoolean(FIRST_RUN, false)
        return isFirstRun
    }
    private  fun getNewWorldRecords(){
        viewModel.getNewWorldData().observe(this, Observer<WorldState> { data ->
            // update UI
            if(data != null){
                homeFragment.setupWorldStats(data)
            }else{
                println("Error Fetching Data")
            }
        })
    }

    private fun getWorldRecordsSavedData(){
        viewModel.getSavedWorldState().observe(this, Observer {
            if (it != null){
                homeFragment.setupWorldStats(it)
            }else{
                Toast.makeText(this,"Error Fetching Data",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getNewData(){
        viewModel.getNewData().observe(this, Observer<List<Country>> { countries ->
            // update UI
            if(!checkFirstRun()){
                val pref =    getPreferences(Context.MODE_PRIVATE)
                val editor = pref.edit()
                editor.putBoolean(FIRST_RUN, false)
                editor.apply()
            }
            if (countries != null) {
                var list = countries
                homeFragment.adapter.clear()
                homeFragment.update(list)
                println("new data")
                homeFragment.adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Error Fetching Data", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getSavedData(){

        viewModel.getSavedData().observe(this, Observer<List<Country>> { countries ->
            // update UI
            if(countries != null){
                homeFragment.update(countries)
            } else {
                Toast.makeText(this, "Error Occured", Toast.LENGTH_LONG).show()
            }

        })
    }

    /*Mostafa End*/

    /*AboElnaga Start*/
    private fun init() {
        setupBottomBar()
        showInitialFragment()
    }

    private fun showInitialFragment() {
        activeFragment = homeFragment

        supportFragmentManager.commit {
            add(R.id.homeContainer, homeFragment, HOME)
        }
    }

    private fun setupBottomBar() {
        bottomNavBar.setOnNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId) {
                R.id.action_overview -> handleHomeAction()
                R.id.action_india -> handleSettingsAction()
            }

            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun handleHomeAction() {
        when (activeFragment) {
            homeFragment -> {
                homeFragment.scrollToTop()
                return
            }
            settingsFragment -> {
                val frag = supportFragmentManager.findFragmentByTag(HOME)

                if (frag == null) {
                    supportFragmentManager.commit {
                        hide(activeFragment)
                        add(R.id.homeContainer, homeFragment, HOME)
                    }
                } else {
                    supportFragmentManager.commit {
                        hide(activeFragment)
                        show(frag)
                    }
                }
            }
        }

        activeFragment = homeFragment
    }

    private fun handleSettingsAction() {
        when (activeFragment) {
            homeFragment -> {
                val frag = supportFragmentManager.findFragmentByTag(SETTINGS)

                if (frag == null) {
                    supportFragmentManager.commit {
                        hide(activeFragment)
                        add(R.id.homeContainer, settingsFragment, SETTINGS)
                    }
                } else {
                    supportFragmentManager.commit {
                        hide(activeFragment)
                        show(frag)
                    }
                }
            }
            settingsFragment -> {
                return
            }
        }

        activeFragment = settingsFragment
    }

    override fun onBackPressed() {
        if (activeFragment == homeFragment) {
            if (homeFragment.handleBackPress()) {

            } else {
                finish()
            }
        } else if (activeFragment == settingsFragment) {
            handleHomeAction()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        private const val HOME = "home"
        private const val SETTINGS = "settings"
    }
    /*AboElnaga End*/
}
