package com.example.covid19_tracker.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.covid19_tracker.MainActivity
import com.example.covid19_tracker.model.Country
import com.example.covid19_tracker.model.WorldState

@Database(entities = [Country::class , WorldState::class],version = 1,exportSchema = false)
abstract class CountryDataBase : RoomDatabase(){
    abstract fun countryDao() : CountryDao

    companion object {
       private var INSTANCE: CountryDataBase? = null

        fun getAppDataBase(context: Context): CountryDataBase? {
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        CountryDataBase::class.java,
                        "myDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}