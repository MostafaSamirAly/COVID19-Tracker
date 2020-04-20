package com.example.covid19_tracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid19_tracker.App
import com.example.covid19_tracker.db.CountryDao
import com.example.covid19_tracker.model.Country
import com.example.covid19_tracker.model.WorldState
import com.example.test.model.CountryState
import com.example.test.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CountryRepositoryImp(private val countryDao: CountryDao) : CountryRepository {
   // private val countryDao: CountryDao? = App.db.countryDao()



    override fun getCountriesFromDB(): LiveData<List<Country>> {
        return countryDao.getCountries()
    }

    override fun getCountriesFromAPI(): LiveData<List<Country>> {

        var data = MutableLiveData<List<Country>>()

         RetrofitClient.instance.getCountries().enqueue(object : Callback<CountryState> {
            override fun onFailure(call: Call<CountryState>?, t: Throwable?) {
                println("on Failure")
                data.value = null
            }
            override fun onResponse(call: Call<CountryState>?, response: Response<CountryState>?) {
                if (response != null) {
                    if (response.isSuccessful)
                        data.value = response.body()?.countries
                        saveData(response.body()?.countries!!)
                }

            }

        })

        return data
    }

    override fun getWorldStatsFromAPI(): LiveData<WorldState> {

        var data = MutableLiveData<WorldState>()

        RetrofitClient.instance.getWorldStats().enqueue(object : Callback<WorldState> {
            override fun onFailure(call: Call<WorldState>?, t: Throwable?) {
                println("on Failure")
                data.value = null
            }
            override fun onResponse(call: Call<WorldState>?, response: Response<WorldState>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        println("on Response")

                        data.value = response.body()?.copy()
                        val worldState = data.value
                        saveWorldState(worldState!!)
                    }
                }

            }

        })

        return data
    }

    override fun getWorldStatsFromDB(): LiveData<WorldState> {
        return countryDao.getWorldState()
    }

    override fun saveData(list: List<Country>) {
        for(country in list){
            countryDao.insertCountry(country)
        }
    }

    override fun saveWorldState(worldState: WorldState) {
        countryDao.insertWorldState(worldState)
    }
}