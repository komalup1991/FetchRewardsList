package com.example.fetchrewardslist.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fetchrewardslist.model.Item
import com.example.fetchrewardslist.model.UiState
import com.example.fetchrewardslist.viewmodel.FetchListViewModel

@Composable
fun ItemScreen(viewModel: FetchListViewModel) {
    val uiStateFlow = viewModel.fetchItems().collectAsState()
    when (val uiState = uiStateFlow.value) {
        UiState.Loading -> LoadingScreen()
        UiState.UpdateEmptyState -> EmptyScreen()
        is UiState.ErrorState -> ErrorScreen(uiState.error)
        is UiState.UpdateList -> ItemList(uiState.list)
    }

}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun EmptyScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Nothing to fetch here!! 🐶")
    }

}

@Composable
fun ErrorScreen(error: String) {
    val context = LocalContext.current

    LaunchedEffect(error) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun ItemList(list: Map<Int, List<Item>>) {
    val expandedStates = remember { mutableStateMapOf<Int, Boolean>() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 16.dp)
    ) {
        list.forEach { (groupId, itemsInGroup) ->
            val isExpanded = expandedStates[groupId] ?: false

            item {
                Text(
                    text = "List ID: $groupId",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable {
                            expandedStates[groupId] = !isExpanded
                        }
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                )
            }

            if (isExpanded) {
                val sortedItems = itemsInGroup.sortedWith(
                    compareBy<Item> { it.id }
                        .thenBy { it.name }
                )

                items(sortedItems) { item ->
                    Text(
                        text = "ID: ${item.id}, Name: ${item.name}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp, horizontal = 12.dp)
                    )
                }
            }
        }
    }
}