package com.example.fetchrewardslist.model

sealed class Response {
    data class Data(val listOfItems: List<Item>): Response()
    data class Error(val error: String): Response()
}