package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.data.model.ExpenseEntity
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExpensesUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(): Flow<List<ExpenseEntity>> {
        return transactionRepository.getAllExpenses()
    }
}
