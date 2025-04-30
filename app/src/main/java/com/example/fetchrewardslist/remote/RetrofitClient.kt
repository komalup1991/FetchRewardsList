package com.example.fetchrewardslist.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val fetchApiService: FetchApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://hiring.fetch.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FetchApiService::class.java)
    }
}