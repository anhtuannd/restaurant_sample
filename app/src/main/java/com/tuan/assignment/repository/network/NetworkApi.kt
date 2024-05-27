package com.tuan.assignment.repository.network

import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {
    @GET(value = "restaurants")
    suspend fun getRestaurants(): ApiResult<RestaurantResponse>

    @GET(value = "filter/{id}")
    suspend fun getFilter(@Path("id") id: String): ApiResult<NetworkFilter>

    @GET(value = "open/{id}")
    suspend fun getOpen(@Path("id") id: String): ApiResult<OpenStatus>
}

sealed class ApiResult<out T: Any> {
    class Success<T: Any>(val data: T?) : ApiResult<T>()
    class Error<T: Any>(val code: Int? = null, val data: QueryError?) : ApiResult<T>()
    class Exception(val e: Throwable) : ApiResult<Nothing>()
}
