package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.domain.model.Expense
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class InsertExpenseUseCaseTest {

    private lateinit var transactionRepository: TransactionRepository
    private lateinit var insertExpenseUseCase: InsertExpenseUseCase

    @Before
    fun setUp() {
        transactionRepository = mockk(relaxed = true)
        insertExpenseUseCase = InsertExpenseUseCase(transactionRepository)
    }

    @Test
    fun `invoke should call insertExpense on repository`() = runTest {
        // Arrange
        val expense = Expense(
            id = 1,
            amount = 100.0,
            description = "Test expense",
            date = System.currentTimeMillis(),
            categoryId = 1L
        )

        // Act
        insertExpenseUseCase(expense)

        // Assert
        coVerify(exactly = 1) { transactionRepository.insertExpense(expense) }
    }
}