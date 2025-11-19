package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.data.model.CategoryWithExpensesEntity
import com.example.appdegestindegastos.domain.model.Category
import com.example.appdegestindegastos.domain.model.CategoryWithExpense
import com.example.appdegestindegastos.domain.model.Expense
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for getting the list of categories with their associated expenses.
 * This class follows the single responsibility principle, where its only purpose is to
 * interact with the repository to get the categories with their expenses.
 */
class GetCategoriesWithExpensesUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    /**
     * Invokes the use case to get the categories with their expenses.
     * @return A flow of the list of categories with their expenses.
     */
    operator fun invoke(): Flow<List<CategoryWithExpense>> {
        return toDomain(transactionRepository.getCategoriesWithExpenses())
    }

    fun toDomain(categoriesWithExpenses: Flow<List<CategoryWithExpensesEntity>>): Flow<List<CategoryWithExpense>> {
        return categoriesWithExpenses.map { listCategoryWithExpenseEntity ->
            listCategoryWithExpenseEntity.map { categoryWithExpenseEntity ->
                CategoryWithExpense(
                    category = Category(
                        categoryWithExpenseEntity.category.id,
                        categoryWithExpenseEntity.category.type
                    ),
                    expenses = categoryWithExpenseEntity.expenses.map {
                        Expense(it.id, it.amount, it.description, it.date, it.categoryId)
                    }
                )
            }
        }
    }
}
