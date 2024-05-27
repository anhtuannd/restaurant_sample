package com.tuan.assignment.repository.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenStatus(
    @SerialName("restaurant_id")
    val restaurantId: String,
    @SerialName("is_currently_open")
    val isCurrentlyOpen: Boolean
)
