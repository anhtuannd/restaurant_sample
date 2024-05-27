package com.tuan.assignment.repository

import com.tuan.assignment.di.ApplicationScope
import com.tuan.assignment.di.DefaultDispatcher
import com.tuan.assignment.repository.local.AssignmentDao
import com.tuan.assignment.repository.local.toFilters
import com.tuan.assignment.repository.network.ApiResult
import com.tuan.assignment.repository.network.NetworkDataSource
import com.tuan.assignment.repository.network.NetworkRestaurant
import com.tuan.assignment.repository.network.toLocal
import com.tuan.assignment.repository.network.toLocalRestaurant
import com.tuan.assignment.repository.network.toRestaurant
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImplRestaurantRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val roomDataSource: AssignmentDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    @ApplicationScope private val scope: CoroutineScope
): RestaurantRepository {

    private suspend fun getFilter(id: String) = withContext(dispatcher) {
        roomDataSource.getFilterById(id) ?: when (val filter = networkDataSource.getFilter(id)) {
            is ApiResult.Success -> filter.data?.let {
                roomDataSource.upsertFilter(filter = it.toLocal(false))
            }
            is ApiResult.Error -> {}
            is ApiResult.Exception -> {}
        }
    }

    override suspend fun fetchRestaurants(): Unit = withContext(dispatcher) {
        when (val result = networkDataSource.getRestaurants()) {
            is ApiResult.Success -> result.data?.let {
                roomDataSource.deleteAllRestaurants()
                val filterIdSet = hashSetOf<String>()
                it.restaurants.forEach { restaurant ->
                    roomDataSource.upsertRestaurant(restaurant.toLocalRestaurant())
                    restaurant.filterIds.forEach { id ->
                        filterIdSet.add(id)
                    }
                }

                filterIdSet.forEach { id ->
                    getFilter(id)
                }

                _restaurants.emit(it.restaurants)
            }
            is ApiResult.Error -> {}
            is ApiResult.Exception -> {}
        }
    }

    private val _restaurants = MutableStateFlow<List<NetworkRestaurant>>(listOf())
    override val filters: Flow<List<Filter>> =
        roomDataSource
            .observeAllFilters()
            .map { localFilters ->
                withContext(dispatcher) {
                    localFilters.toFilters()
                }
            }

    override suspend fun setFilterSelected(id: String, selected: Boolean) {
        roomDataSource.setFilterSelected(id, selected)
    }

    override val restaurants = combine(_restaurants, filters) { restaurants, filters ->
        withContext(dispatcher) {
            val isFilterEnabled = filters.any { filter -> filter.selected }
            restaurants.mapNotNull { networkRestaurant ->
                val restaurantFilters = networkRestaurant.filterIds.mapNotNull { id ->
                    filters.firstOrNull() {
                        it.id == id
                    }
                }
                networkRestaurant.toRestaurant(restaurantFilters)
            }.filter {
                it.filters.any { filter -> filter.selected } || !isFilterEnabled
            }
        }
    }

}