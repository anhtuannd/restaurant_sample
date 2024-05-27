package com.tuan.assignment.repository

data class Restaurant(
    val id: String,
    val name: String,
    val rating: Float,
    val filters: List<Filter>,
    val imageUrl: String,
    val deliveryTimeInMinutes: Int
)
