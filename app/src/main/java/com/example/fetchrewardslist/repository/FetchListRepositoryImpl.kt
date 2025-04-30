package com.example.fetchrewardslist.repository

import com.example.fetchrewardslist.model.Item
import com.example.fetchrewardslist.model.Response
import com.example.fetchrewardslist.remote.FetchApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class FetchListRepositoryImpl(private val fetchApiService: FetchApiService) : FetchListRepository {
    override fun fetchItems(): Flow<Response> = flow {
        try {
            val response = fetchApiService.fetchItems().filter { !it.name.isNullOrBlank() }
            emit(Response.Data(response))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Unknown error"))
        }
    }
}