package com.example.fetchrewardslist.viewmodel

import com.example.fetchrewardslist.model.Item
import com.example.fetchrewardslist.model.Response
import com.example.fetchrewardslist.model.UiState
import com.example.fetchrewardslist.repository.FetchListRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class FetchListViewModelTest {
    private lateinit var repository: FetchListRepository
    private lateinit var viewModel: FetchListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchItems returns UpdateList when repository returns data`() = runTest {
        val item = Item(id = 1, listId = 1, name = "Item 1")
        val responseFlow = flowOf(Response.Data(listOf(item)))
        coEvery { repository.fetchItems() } returns responseFlow
        viewModel = FetchListViewModel(repository)
        val state = viewModel.fetchItems().first()

        assertTrue(state is UiState.UpdateList)
        val data = (state as UiState.UpdateList).list
        assertEquals(1, data.size)
        assertEquals(listOf(item), data[1])
    }

    @Test
    fun `fetchItems returns ErrorState when repository returns error`() = runTest {
        val errorMsg = "Network Error"
        val responseFlow = flowOf(Response.Error(errorMsg))
        coEvery { repository.fetchItems() } returns responseFlow
        viewModel = FetchListViewModel(repository)
        val state = viewModel.fetchItems().first()

        assertTrue(state is UiState.ErrorState)
        assertEquals(errorMsg, (state as UiState.ErrorState).error)
    }

    @Test
    fun `initial state is Loading`() = runTest {
        coEvery { repository.fetchItems() } returns emptyFlow()
        viewModel = FetchListViewModel(repository)

        val state = viewModel.fetchItems().first()
        assertTrue(state is UiState.Loading)
    }

    @Test
    fun `fetchItems returns UpdateEmptyState when list is empty`() = runTest {
        val responseFlow = flowOf(Response.Data(emptyList()))
        coEvery { repository.fetchItems() } returns responseFlow
        viewModel = FetchListViewModel(repository)
        val state = viewModel.fetchItems().first()

        assertTrue(state is UiState.UpdateEmptyState)
    }
}