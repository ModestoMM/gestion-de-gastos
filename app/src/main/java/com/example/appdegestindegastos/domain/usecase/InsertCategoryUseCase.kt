package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import javax.inject.Inject

class InsertCategoryUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(category: CategoryEntity) {
        transactionRepository.insertCategory(category)
    }
}
