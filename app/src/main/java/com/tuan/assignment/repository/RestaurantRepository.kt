package com.tuan.assignment.repository

import kotlinx.coroutines.flow.Flow

interface RestaurantRepository {
    val restaurants: Flow<List<Restaurant>>
    val filters: Flow<List<Filter>>

    suspend fun setFilterSelected(id: String, selected: Boolean)
    suspend fun fetchRestaurants()
}