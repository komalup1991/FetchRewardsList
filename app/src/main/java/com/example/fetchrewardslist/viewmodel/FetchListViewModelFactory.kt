package com.example.fetchrewardslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fetchrewardslist.repository.FetchListRepository

class FetchListViewModelFactory(
    private val repository: FetchListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FetchListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FetchListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}