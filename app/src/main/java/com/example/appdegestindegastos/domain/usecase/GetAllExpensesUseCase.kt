package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.data.model.ExpenseEntity
import com.example.appdegestindegastos.domain.model.Expense
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case for getting the list of all expenses.
 * This class follows the single responsibility principle, where its only purpose is to
 * interact with the repository to get all expenses.
 */
class GetAllExpensesUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    /**
     * Invokes the use case to get all expenses.
     * @return A flow of the list of all expenses.
     */
    operator fun invoke(): Flow<List<Expense>> {
        return toDomain(transactionRepository.getAllExpenses())
    }

    fun toDomain(allExpenses: Flow<List<ExpenseEntity>>): Flow<List<Expense>> {
        return allExpenses.map { listExpenseEntity ->
            listExpenseEntity.map { expenseEntity ->
                Expense(
                    expenseEntity.id,
                    expenseEntity.amount,
                    expenseEntity.description,
                    expenseEntity.date,
                    expenseEntity.categoryId
                )
            }
        }
    }
}
