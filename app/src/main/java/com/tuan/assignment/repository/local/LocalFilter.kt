package com.tuan.assignment.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tuan.assignment.repository.Filter

@Entity(
    tableName = "filters"
)
data class LocalFilter(
    @PrimaryKey val id: String,
    val name: String,
    val imageUrl: String,
    val selected: Boolean,
)
fun Filter.toLocal() = LocalFilter(id, name, imageUrl, selected)
fun LocalFilter.toFilter() = Filter(id, name, imageUrl, selected)
fun List<LocalFilter>.toFilters() = map { localFilter -> localFilter.toFilter() }
