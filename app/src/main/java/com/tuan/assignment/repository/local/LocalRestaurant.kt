package com.tuan.assignment.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tuan.assignment.repository.Filter
import com.tuan.assignment.repository.Restaurant

@Entity(
    tableName = "restaurants"
)
data class LocalRestaurant(
    @PrimaryKey val id: String,
    val name: String,
    val rating: Float,
    val imageUrl: String,
    val deliveryTimeInMinutes: Int,
    val filters: String
    )

fun LocalRestaurant.toRestaurant() = toRestaurant(listOf())

fun LocalRestaurant.toRestaurant(filters: List<Filter>) =
    Restaurant(
        id = id,
        name = name,
        rating = rating,
        imageUrl = imageUrl,
        filters = filters,
        deliveryTimeInMinutes = deliveryTimeInMinutes
    )