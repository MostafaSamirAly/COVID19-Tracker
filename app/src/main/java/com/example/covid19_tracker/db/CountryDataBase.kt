package com.example.covid19_tracker.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.covid19_tracker.MainActivity
import com.example.covid19_tracker.model.Country

@Database(entities = [Country::class],version = 1,exportSchema = false)
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
        /*
        companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(context: Context): WordRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordRoomDatabase::class.java,
                        "word_database"
                    ).build()
                INSTANCE = instance
                return instance
            }
        }
   }
        * */

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}