package com.example.fetchrewardslist.remote

import com.example.fetchrewardslist.model.Item
import retrofit2.http.GET

interface FetchApiService {
    @GET("hiring.json")
    suspend fun fetchItems(): List<Item>
}