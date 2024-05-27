package com.tuan.assignment.repository

import kotlinx.coroutines.flow.Flow

interface DetailRepository {
    fun getOpenStatus(id: String): Flow<Boolean>
    fun getRestaurant(id: String): Flow<Restaurant>

}