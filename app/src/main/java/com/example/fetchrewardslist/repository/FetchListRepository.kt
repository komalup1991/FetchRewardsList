package com.example.fetchrewardslist.repository

import com.example.fetchrewardslist.model.Item
import kotlinx.coroutines.flow.Flow

interface FetchListRepository {
    fun fetchItems() : Flow<List<Item>>
}