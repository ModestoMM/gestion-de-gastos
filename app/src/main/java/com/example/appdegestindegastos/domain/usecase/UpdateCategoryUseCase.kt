package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.domain.model.Category
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(category: Category) {
        transactionRepository.updateCategory(category)
    }
}
