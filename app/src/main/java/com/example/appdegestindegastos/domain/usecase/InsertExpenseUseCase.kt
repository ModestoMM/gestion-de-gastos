package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.data.model.ExpenseEntity
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import javax.inject.Inject

class InsertExpenseUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(expense: ExpenseEntity) {
        transactionRepository.insertExpense(expense)
    }
}
