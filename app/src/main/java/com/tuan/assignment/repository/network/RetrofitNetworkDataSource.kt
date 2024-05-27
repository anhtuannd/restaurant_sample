package com.tuan.assignment.repository.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tuan.assignment.repository.network.retrofit.NetworkResultAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitNetworkDataSource @Inject constructor(networkJson: Json): NetworkDataSource {
    private val networkApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(NetworkResultAdapterFactory.create())
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(NetworkApi::class.java)

    override suspend fun getRestaurants(): ApiResult<RestaurantResponse> = networkApi.getRestaurants()

    override suspend fun getOpenStatus(id: String): ApiResult<OpenStatus> = networkApi.getOpen(id)

    override suspend fun getFilter(id: String): ApiResult<NetworkFilter> = networkApi.getFilter(id)

    companion object {
        const val BASE_URL = "https://food-delivery.umain.io/api/v1/"
    }
}