package com.example.covid19_tracker.repository

import androidx.lifecycle.LiveData
import com.example.covid19_tracker.model.Country
import com.example.covid19_tracker.model.WorldState

interface CountryRepository {

    fun getCountriesFromDB() : LiveData<List<Country>>

    fun getCountriesFromAPI() : LiveData<List<Country>>

    fun getWorldStatsFromAPI() : LiveData<WorldState>

    fun saveData(list : List<Country>)
}