package com.example.covid19_tracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.covid19_tracker.model.Country
import com.example.covid19_tracker.model.WorldState

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE , entity = Country::class)
    fun insertCountry(country: Country)

    @Update
    fun updateCountry(country: Country)

    @Delete
    fun deleteCountry(country: Country)

    @Query("SELECT * FROM Country WHERE country_name == :name")
    fun getCountryByName(name: String): List<Country>

    @Query("SELECT * FROM Country")
    fun getCountries(): LiveData<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE , entity = WorldState::class)
    fun insertWorldState(worldState : WorldState)

    @Query("SELECT * FROM WorldState")
    fun getWorldState(): LiveData<WorldState>

}