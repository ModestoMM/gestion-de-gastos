package com.example.appdegestindegastos.domain.repository

import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.data.model.CategoryWithExpenses
import com.example.appdegestindegastos.data.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun insertExpense(expense: ExpenseEntity)
    suspend fun insertCategory(category: CategoryEntity)
    suspend fun updateExpense(expense: ExpenseEntity)
    suspend fun updateCategory(category: CategoryEntity)
    fun getCategoriesWithExpenses(): Flow<List<CategoryWithExpenses>>
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
}
