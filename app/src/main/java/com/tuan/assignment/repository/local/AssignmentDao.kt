package com.tuan.assignment.repository.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {
    @Query("SELECT * FROM filters")
    fun observeAllFilters(): Flow<List<LocalFilter>>

    @Query("SELECT * FROM filters WHERE id = :filterId")
    fun observeFilterById(filterId: String): Flow<LocalFilter>

    @Query("SELECT * FROM filters")
    suspend fun getAllFilter(): List<LocalFilter>

    @Query("SELECT * FROM filters WHERE id = :filterId")
    suspend fun getFilterById(filterId: String): LocalFilter?

    @Upsert
    suspend fun upsertFilter(filter: LocalFilter)

    @Query("UPDATE filters SET selected = :selected WHERE id = :id")
    suspend fun setFilterSelected(id: String, selected: Boolean)

    @Query("DELETE FROM filters")
    suspend fun deleteAllFilters()

    @Query("SELECT * FROM restaurants WHERE id = :restaurantId")
    fun observeRestaurantById(restaurantId: String): Flow<LocalRestaurant>

    @Upsert
    suspend fun upsertRestaurant(restaurant: LocalRestaurant)

    @Query("DELETE FROM restaurants")
    suspend fun deleteAllRestaurants()
}