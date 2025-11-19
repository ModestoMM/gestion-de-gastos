package com.example.appdegestindegastos.data.repository

import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.data.model.CategoryWithExpensesEntity
import com.example.appdegestindegastos.data.model.ExpenseEntity
import com.example.appdegestindegastos.domain.model.Expense
import com.example.appdegestindegastos.data.room.TransactionDao
import com.example.appdegestindegastos.domain.model.Category
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override suspend fun insertExpense(expense: Expense) {
        transactionDao.insertExpense(toExpenseEntity(expense))
    }

    override suspend fun insertCategory(category: Category) {
        transactionDao.insertCategory(toCategoryEntity(category))
    }

    override suspend fun updateExpense(expense: Expense) {
        transactionDao.updateExpense(toExpenseEntity(expense))
    }

    override suspend fun updateCategory(category: Category) {
        transactionDao.updateCategory(toCategoryEntity(category))
    }

    override fun getCategoriesWithExpenses(): Flow<List<CategoryWithExpensesEntity>> {
        return transactionDao.getCategoriesWithExpenses()
    }

    override fun getAllExpenses(): Flow<List<ExpenseEntity>> {
        return transactionDao.getAllExpenses()
    }

    fun toExpenseEntity(expense: Expense): ExpenseEntity {
        return ExpenseEntity(
            expense.id,
            expense.amount,
            expense.description,
            expense.date,
            expense.categoryId
        )
    }

    fun toCategoryEntity(category: Category): CategoryEntity {
        return CategoryEntity(
            category.id,
            category.type
        )
    }
}
