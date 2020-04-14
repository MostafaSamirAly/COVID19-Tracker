package com.example.covid19_tracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.covid19_tracker.model.Country

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountry(Country: Country)

    @Update
    fun updateCountry(gender: Country)

    @Delete
    fun deleteCountry(gender: Country)

    @Query("SELECT * FROM Country WHERE country_name == :name")
    fun getCountryByName(name: String): List<Country>

    @Query("SELECT * FROM Country")
    fun getCountries(): LiveData<List<Country>>
}