package com.example.fetchrewardslist

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fetchrewardslist.remote.RetrofitClient
import com.example.fetchrewardslist.repository.FetchListRepository
import com.example.fetchrewardslist.repository.FetchListRepositoryImpl
import com.example.fetchrewardslist.ui.theme.FetchRewardsListTheme
import com.example.fetchrewardslist.viewmodel.FetchListViewModel
import com.example.fetchrewardslist.viewmodel.FetchListViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: FetchListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = FetchListRepositoryImpl(RetrofitClient.fetchApiService)
        val factory = FetchListViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[FetchListViewModel::class.java]

        lifecycleScope.launch {
            viewModel.fetchItems().collectLatest { items ->
                Log.d("KOMAL", "item groups = " + items.size)
            }
        }

        enableEdgeToEdge()
        setContent {
            FetchRewardsListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FetchRewardsListTheme {
        Greeting("Android")
    }
}