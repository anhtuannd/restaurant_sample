package com.tuan.assignment.repository.network

interface NetworkDataSource {
    suspend fun getRestaurants(): ApiResult<RestaurantResponse>

    suspend fun getOpenStatus(id: String): ApiResult<OpenStatus>

    suspend fun getFilter(id: String): ApiResult<NetworkFilter>

}
