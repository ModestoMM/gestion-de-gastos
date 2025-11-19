package com.example.appdegestindegastos.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.data.model.ExpenseEntity

@Database(
    entities = [ExpenseEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)

abstract class TransactionBd : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
