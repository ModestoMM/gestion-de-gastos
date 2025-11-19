package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.data.model.ExpenseEntity
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import javax.inject.Inject

/**
 * Use case for inserting an expense into the database.
 * This class follows the single responsibility principle, where its only purpose is to
 * interact with the repository to insert an expense.
 */
class InsertExpenseUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    /**
     * Invokes the use case to insert an expense.
     * @param expense The expense to be inserted.
     */
    suspend operator fun invoke(expense: ExpenseEntity) {
        transactionRepository.insertExpense(expense)
    }
}
