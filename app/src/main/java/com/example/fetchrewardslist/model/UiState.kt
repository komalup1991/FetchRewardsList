package com.example.fetchrewardslist.model

sealed class UiState {
    data object Loading : UiState()
    data object UpdateEmptyState : UiState()
    data class ErrorState(val error: String) : UiState()
    data class UpdateList(val list: Map<Int, List<Item>>) : UiState()
}