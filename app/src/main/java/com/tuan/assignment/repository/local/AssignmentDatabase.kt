package com.tuan.assignment.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocalFilter::class, LocalRestaurant::class],
    version = 1,
    exportSchema = false
)
abstract class AssignmentDatabase: RoomDatabase() {
    abstract fun dao(): AssignmentDao
}