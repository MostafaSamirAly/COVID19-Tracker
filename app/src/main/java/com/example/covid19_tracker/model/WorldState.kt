package com.example.covid19_tracker.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class WorldState (

    @SerializedName("total_cases") val total_cases: String,
    @SerializedName("total_deaths") val total_death: String,
    @SerializedName("total_recovered") val total_recovered: String
/*
    @PrimaryKey
    @NonNull
    val total_cases: String,

    @ColumnInfo
    val total_deaths: String?,

    @ColumnInfo
    val total_recovered: String?,

    @ColumnInfo
    val new_cases: String?,

    @ColumnInfo
    val new_deaths: String?,

    @ColumnInfo
    val statistic_taken_at: String?
*/
)
