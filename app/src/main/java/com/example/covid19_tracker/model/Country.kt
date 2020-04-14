package com.example.covid19_tracker.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey
    @NonNull
    val country_name: String,

    @ColumnInfo
    val cases: String?,

    @ColumnInfo
    val deaths: String?,

    @ColumnInfo
    val new_deaths: String?,

    @ColumnInfo
    val total_recovered: String?,

    @ColumnInfo
    val new_cases: String?,

    @ColumnInfo
    val active_cases: String?,

    @ColumnInfo
    val region: String?,

    @ColumnInfo
    val serious_critical: String?,

    @ColumnInfo
    val total_cases_per_1m_population: String?)


