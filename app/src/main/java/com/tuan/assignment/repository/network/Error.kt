package com.tuan.assignment.repository.network

import kotlinx.serialization.Serializable

@Serializable
data class QueryError(
    val error: Boolean,
    val reason: String?
)
