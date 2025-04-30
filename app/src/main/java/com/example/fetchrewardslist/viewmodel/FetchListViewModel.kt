package com.example.fetchrewardslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchrewardslist.model.Response
import com.example.fetchrewardslist.model.UiState
import com.example.fetchrewardslist.repository.FetchListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FetchListViewModel(private val repository: FetchListRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    private val uiState: StateFlow<UiState> = _uiState

    fun fetchItems(): StateFlow<UiState> {
        viewModelScope.launch {
            repository.fetchItems().collectLatest { response ->
                when (response) {
                    is Response.Data -> {
                        val list = response.listOfItems
                        val sorted = list.groupBy { it.listId }.toSortedMap(compareBy { it })
                        if (!sorted.isEmpty()) {
                            _uiState.value = UiState.UpdateList(sorted)
                        } else {
                            _uiState.value = UiState.UpdateEmptyState
                        }
                    }
                    is Response.Error -> {
                        _uiState.value = UiState.ErrorState(response.error)
                    }
                }
            }
        }
        return uiState
    }
}