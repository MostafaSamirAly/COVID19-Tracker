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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*Mostafa Start*/
        //viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        /*Mostafa End*/

        /*AboElnaga Start*/
        init()
        /*AboElnaga End*/
    }

    override fun onStart() {
        super.onStart()



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
