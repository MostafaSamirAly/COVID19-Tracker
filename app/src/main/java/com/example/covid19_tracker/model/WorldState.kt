package com.example.covid19_tracker.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity
data class WorldState (

    @PrimaryKey
    @NonNull
    val id : Int,

    @ColumnInfo
    val total_cases: String,

    @ColumnInfo
    val total_deaths: String,

    @ColumnInfo
    val total_recovered: String

)
