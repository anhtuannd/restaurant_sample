package com.tuan.assignment.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuan.assignment.navigation.RestaurantArgs
import com.tuan.assignment.repository.DetailRepository
import com.tuan.assignment.repository.Restaurant
import com.tuan.assignment.repository.Result
import com.tuan.assignment.repository.mapResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: DetailRepository
) : ViewModel() {

    private val restaurantArgs: RestaurantArgs = RestaurantArgs(savedStateHandle)
    val id = restaurantArgs.id
    val detailUiState: StateFlow<DetailUiState> = repository.getRestaurant(id)
        .mapResult()
        .map { detailResult ->
            when (detailResult) {
                is Result.Success -> DetailUiState.Success(detailResult.data)
                is Result.Error -> DetailUiState.Error(detailResult.exception)
                is Result.Loading -> DetailUiState.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DetailUiState.Loading,
        )

    val openUiState: StateFlow<OpenUiState> = repository.getOpenStatus(id)
        .mapResult()
        .map { detailResult ->
            when (detailResult) {
                is Result.Success -> OpenUiState.Success(detailResult.data)
                is Result.Error -> OpenUiState.Error(detailResult.exception)
                is Result.Loading -> OpenUiState.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = OpenUiState.Loading,
        )
}

sealed interface DetailUiState {
    data class Success(val data: Restaurant): DetailUiState
    data class Error(val throwable: Throwable) : DetailUiState
    data object Loading : DetailUiState
}

sealed interface OpenUiState {
    data class Success(val data: Boolean): OpenUiState
    data class Error(val throwable: Throwable) : OpenUiState
    data object Loading : OpenUiState
}