package com.tuan.assignment.repository

import com.tuan.assignment.di.DefaultDispatcher
import com.tuan.assignment.repository.local.AssignmentDao
import com.tuan.assignment.repository.local.toFilter
import com.tuan.assignment.repository.local.toRestaurant
import com.tuan.assignment.repository.network.ApiResult
import com.tuan.assignment.repository.network.NetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImplDetailRepository @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    private val roomDataSource: AssignmentDao,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
): DetailRepository {
    override fun getOpenStatus(id: String): Flow<Boolean> = flow {
        when (val status = networkDataSource.getOpenStatus(id)) {
            is ApiResult.Success -> status.data?.isCurrentlyOpen?.let { emit(it) }
            is ApiResult.Error -> {}
            is ApiResult.Exception -> {}
        }
    }

    override fun getRestaurant(id: String): Flow<Restaurant> =
        roomDataSource
            .observeRestaurantById(id)
            .map {
                withContext(dispatcher) {
                    val filterList = it.filters.split(",").mapNotNull {
                        roomDataSource.getFilterById(it)?.toFilter()
                    }
                    it.toRestaurant(filterList)
                }
            }
}