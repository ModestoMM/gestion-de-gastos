package com.example.appdegestindegastos.domain.repository

import com.example.appdegestindegastos.domain.model.Category
import com.example.appdegestindegastos.data.model.CategoryWithExpensesEntity
import com.example.appdegestindegastos.data.model.ExpenseEntity
import com.example.appdegestindegastos.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun insertExpense(expense: Expense)
    suspend fun insertCategory(category: Category)
    suspend fun updateExpense(expense: Expense)
    suspend fun updateCategory(category: Category)
    fun getCategoriesWithExpenses(): Flow<List<CategoryWithExpensesEntity>>
    fun getAllExpenses(): Flow<List<ExpenseEntity>>
}
