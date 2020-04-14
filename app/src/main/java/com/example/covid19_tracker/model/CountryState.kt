package com.example.test.model

import com.example.covid19_tracker.model.Country
import com.google.gson.annotations.SerializedName

data class CountryState(
    @SerializedName("countries_stat")
    val countries : List<Country>,
    val statistic_taken_at : String)