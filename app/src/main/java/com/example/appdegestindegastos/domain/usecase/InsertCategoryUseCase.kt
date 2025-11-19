package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import javax.inject.Inject

/**
 * Use case for inserting a category into the database.
 * This class follows the single responsibility principle, where its only purpose is to
 * interact with the repository to insert a category.
 */
class InsertCategoryUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    /**
     * Invokes the use case to insert a category.
     * @param category The category to be inserted.
     */
    suspend operator fun invoke(category: CategoryEntity) {
        transactionRepository.insertCategory(category)
    }
}
