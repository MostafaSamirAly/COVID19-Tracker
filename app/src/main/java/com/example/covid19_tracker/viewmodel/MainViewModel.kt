package com.example.covid19_tracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.covid19_tracker.db.CountryDao
import com.example.covid19_tracker.db.CountryDataBase
import com.example.covid19_tracker.model.Country
import com.example.covid19_tracker.model.WorldState
import com.example.covid19_tracker.repository.CountryRepository
import com.example.covid19_tracker.repository.CountryRepositoryImp

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repo : CountryRepositoryImp
    private val countryDao : CountryDao

    init {
        countryDao = CountryDataBase.getAppDataBase(application)?.countryDao()!!
        repo = CountryRepositoryImp(countryDao)
    }

    fun getSavedData() : LiveData<List<Country>>{
        return repo.getCountriesFromDB()
    }

    fun getNewData() : LiveData<List<Country>>{
        return repo.getCountriesFromAPI()
    }

    fun getNewWorldData() : LiveData<WorldState>{
        return repo.getWorldStatsFromAPI()
    }

    fun getSavedWorldState() : LiveData<List<WorldState>>{
        return repo.getWorldStatsFromDB()
    }





}