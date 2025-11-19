package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.domain.model.Expense
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import javax.inject.Inject

class UpdateExpenseUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(expense: Expense) {
        transactionRepository.updateExpense(expense)
    }
}
