package com.example.fetchrewardslist.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fetchrewardslist.viewmodel.FetchListViewModel

@Composable
fun ItemScreen(viewModel: FetchListViewModel) {
    val groupedItems by viewModel.fetchItems().collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchItems()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                WindowInsets.statusBars.asPaddingValues()
            )
            .padding(horizontal = 16.dp)
    ) {
        groupedItems.forEach { (groupId, itemsInGroup) ->
            item {
                Text(
                    text = "List ID: $groupId",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                )
            }

            items(itemsInGroup) { item ->
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