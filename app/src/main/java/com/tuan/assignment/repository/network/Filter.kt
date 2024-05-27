package com.tuan.assignment.repository.network

import com.tuan.assignment.repository.Filter
import com.tuan.assignment.repository.local.LocalFilter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkFilter(
    val id: String,
    val name: String,
    @SerialName("image_url")
    val imageUrl: String
)

fun NetworkFilter.toFilter() = Filter(id, name, imageUrl)
fun NetworkFilter.toLocal(selected: Boolean) = LocalFilter(id, name, imageUrl, selected)
