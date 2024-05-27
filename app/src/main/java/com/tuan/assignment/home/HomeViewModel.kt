package com.tuan.assignment.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuan.assignment.repository.ImplRestaurantRepository
import com.tuan.assignment.repository.RestaurantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RestaurantRepository) : ViewModel() {

    init {
        fetchRestaurants()
    }

    fun fetchRestaurants() = viewModelScope.launch {
        repository.fetchRestaurants()
    }

    fun setFilterSelected(id: String, selected: Boolean) = viewModelScope.launch {
        repository.setFilterSelected(id, selected)
    }

    val restaurantStream = repository.restaurants.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = listOf(),
    )

    val filterStream = repository.filters.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = listOf(),
    )
}

sealed interface LoadingUiState {
    data class Success(val content: String?): LoadingUiState
    data class Error(val throwable: Throwable?) : LoadingUiState
    data object Loading : LoadingUiState
    data object Idle : LoadingUiState
}