package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.data.model.CategoryWithExpenses
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
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
    operator fun invoke(): Flow<List<CategoryWithExpenses>> {
        return transactionRepository.getCategoriesWithExpenses()
    }
}
