package com.example.appdegestindegastos.data.repository

import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.data.model.CategoryWithExpenses
import com.example.appdegestindegastos.data.model.ExpenseEntity
import com.example.appdegestindegastos.data.room.TransactionDao
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun insertExpense(expense: ExpenseEntity) {
        transactionDao.insertExpense(expense)
    }

    override suspend fun insertCategory(category: CategoryEntity) {
        transactionDao.insertCategory(category)
    }

    override suspend fun updateExpense(expense: ExpenseEntity) {
        transactionDao.updateExpense(expense)
    }

    override suspend fun updateCategory(category: CategoryEntity) {
        transactionDao.updateCategory(category)
    }

    override fun getCategoriesWithExpenses(): Flow<List<CategoryWithExpenses>> {
        return transactionDao.getCategoriesWithExpenses()
    }

    override fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return transactionDao.getAllExpenses()
    }
}
