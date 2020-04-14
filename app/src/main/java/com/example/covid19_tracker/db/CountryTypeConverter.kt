package com.example.covid19_tracker.db

import androidx.room.TypeConverter
import com.example.covid19_tracker.model.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CountryTypeConverter {
    @TypeConverter
    fun StringtoCountryType(data : String) : List<Country> {
        var gson : Gson = Gson()
        if (data.length != 0 && data != "null") {
            val listType = object : TypeToken<List<Country?>?>() {}.type
            return gson.fromJson<List<Country>>(data, listType)
        }
        return ArrayList<Country>()
    }

    @TypeConverter
    fun myObjectsToStoredString(countries: List<Country?>?): String? {
        val gson = Gson()
        return gson.toJson(countries)
    }

}