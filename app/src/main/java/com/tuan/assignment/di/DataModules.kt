package com.tuan.assignment.di

import android.content.Context
import androidx.room.Room
import com.tuan.assignment.repository.DetailRepository
import com.tuan.assignment.repository.ImplDetailRepository
import com.tuan.assignment.repository.ImplRestaurantRepository
import com.tuan.assignment.repository.RestaurantRepository
import com.tuan.assignment.repository.local.AssignmentDao
import com.tuan.assignment.repository.local.AssignmentDatabase
import com.tuan.assignment.repository.network.NetworkDataSource
import com.tuan.assignment.repository.network.RetrofitNetworkDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAssignmentRepository(repository: ImplRestaurantRepository): RestaurantRepository

    @Singleton
    @Binds
    abstract fun bindDetailRepository(repository: ImplDetailRepository): DetailRepository
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindNetworkDataSource(dataSource: RetrofitNetworkDataSource): NetworkDataSource
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAssignmentDatabase(@ApplicationContext context: Context): AssignmentDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AssignmentDatabase::class.java,
            "assignment.db"
        ).build()
    }

    @Provides
    fun provideDbDao(database: AssignmentDatabase): AssignmentDao = database.dao()
}
