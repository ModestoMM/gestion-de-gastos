package com.example.appdegestindegastos.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.data.model.CategoryWithExpenses
import com.example.appdegestindegastos.data.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithExpenses(): Flow<List<CategoryWithExpenses>>

    @Transaction
    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

}
