package com.example.fetchrewardslist.ui.components

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fetchrewardslist.remote.RetrofitClient
import com.example.fetchrewardslist.repository.FetchListRepositoryImpl
import com.example.fetchrewardslist.ui.theme.FetchRewardsListTheme
import com.example.fetchrewardslist.viewmodel.FetchListViewModel
import com.example.fetchrewardslist.viewmodel.FetchListViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: FetchListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = FetchListRepositoryImpl(RetrofitClient.fetchApiService)
        val factory = FetchListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[FetchListViewModel::class.java]

        enableEdgeToEdge()
        setContent {
            FetchRewardsListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ItemScreen(viewModel = viewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }
}


