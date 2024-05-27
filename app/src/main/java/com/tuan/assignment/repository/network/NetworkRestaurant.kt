package com.tuan.assignment.repository.network

import com.tuan.assignment.repository.Filter
import com.tuan.assignment.repository.Restaurant
import com.tuan.assignment.repository.local.LocalRestaurant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkRestaurant(
    val id: String,
    val name: String,
    val rating: Float,
    val filterIds: List<String>,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("delivery_time_minutes")
    val deliveryTimeMinutes: Int
)

fun NetworkRestaurant.toRestaurant(filters: List<Filter>) = Restaurant(
    id = id,
    name = name,
    rating = rating,
    filters = filters,
    imageUrl = imageUrl,
    deliveryTimeInMinutes = deliveryTimeMinutes
)

fun NetworkRestaurant.toLocalRestaurant() = LocalRestaurant(
    id = id,
    name = name,
    rating = rating,
    imageUrl = imageUrl,
    deliveryTimeInMinutes = deliveryTimeMinutes,
    filters = filterIds.joinToString(separator = ",")
)

@Serializable
data class RestaurantResponse(
    val restaurants: List<NetworkRestaurant>
)
