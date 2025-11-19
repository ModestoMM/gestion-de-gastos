package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.data.model.CategoryWithExpenses
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesWithExpensesUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(): Flow<List<CategoryWithExpenses>> {
        return transactionRepository.getCategoriesWithExpenses()
    }
}
