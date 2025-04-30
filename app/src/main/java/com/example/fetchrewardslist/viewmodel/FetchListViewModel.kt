package com.example.fetchrewardslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchrewardslist.model.Item
import com.example.fetchrewardslist.repository.FetchListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FetchListViewModel(private val repository: FetchListRepository) : ViewModel() {
    private val _items = MutableStateFlow<Map<Int, List<Item>>>(emptyMap())
    private val items: StateFlow<Map<Int, List<Item>>> = _items

    fun fetchItems(): StateFlow<Map<Int, List<Item>>> {
        viewModelScope.launch {
            repository.fetchItems().collectLatest { list ->
                val sorted = list.groupBy { it.listId }.toSortedMap(compareBy { it })
                _items.value = sorted
            }
        }
        return items
    }
}