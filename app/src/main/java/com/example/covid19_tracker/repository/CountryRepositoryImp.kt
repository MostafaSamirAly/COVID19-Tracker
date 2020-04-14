package com.example.covid19_tracker.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.covid19_tracker.App
import com.example.covid19_tracker.db.CountryDao
import com.example.covid19_tracker.model.Country
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

    override fun saveData(list: List<Country>) {
        for(country in list){
            println(country.country_name)
            countryDao.insertCountry(country)
        }
    }
}