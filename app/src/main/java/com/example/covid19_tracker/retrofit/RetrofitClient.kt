package com.example.test.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://coronavirus-monitor.p.rapidapi.com/coronavirus/"
    private const val API_KEY = "e53a523f8cmsh07a36f3ccf3b005p103483jsn0b95284a5df1"
    private const val API_HOST = "coronavirus-monitor.p.rapidapi.com"

        val httpClient = OkHttpClient.Builder()
        .addInterceptor({
            val original = it.request()
            val requestBuilder = original.newBuilder()
                .addHeader("x-rapidapi-key", API_KEY)
                .addHeader("x-rapidapi-host", API_HOST)
                .method(original.method() , original.body() )
            val request = requestBuilder.build()
            it.proceed(request)
        }).build()



    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
}