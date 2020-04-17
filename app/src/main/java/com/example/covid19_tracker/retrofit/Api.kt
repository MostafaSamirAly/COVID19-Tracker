package com.example.test.retrofit

import com.example.covid19_tracker.model.WorldState
import com.example.test.model.CountryState
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface Api {

    @GET("cases_by_country.php")
    fun getCountries() : Call<CountryState>

    @GET("worldstat.php")
    fun getWorldStats() : Call<WorldState>
}