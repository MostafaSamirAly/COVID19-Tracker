package com.example.covid19_tracker

import android.app.Application
import com.example.covid19_tracker.db.CountryDataBase

object App : Application(){
    var db : CountryDataBase = CountryDataBase.getAppDataBase(this)!!
    var instance: App = this
    }

