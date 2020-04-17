package com.example.covid19_tracker

import android.app.Application
import com.example.covid19_tracker.db.CountryDataBase

object App : Application(){
    var db : CountryDataBase = CountryDataBase.getAppDataBase(this)!!
    var instance: App = this

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    /*
    companion object {
        lateinit var instance: App
            private set
    }*/
}

