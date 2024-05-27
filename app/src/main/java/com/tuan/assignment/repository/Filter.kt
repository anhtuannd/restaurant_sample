package com.tuan.assignment.repository

data class Filter(
    val id: String,
    val name: String,
    val imageUrl: String,
    val selected: Boolean = false
)
