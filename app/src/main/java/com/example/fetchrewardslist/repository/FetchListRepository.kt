package com.example.fetchrewardslist.repository

import com.example.fetchrewardslist.model.Item
import com.example.fetchrewardslist.model.Response
import kotlinx.coroutines.flow.Flow

interface FetchListRepository {
    fun fetchItems() : Flow<Response>
}