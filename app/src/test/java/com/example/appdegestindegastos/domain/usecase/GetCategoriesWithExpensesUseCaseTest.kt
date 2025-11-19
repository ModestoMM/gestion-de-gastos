package com.example.appdegestindegastos.domain.usecase

import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.data.model.CategoryWithExpensesEntity
import com.example.appdegestindegastos.data.model.ExpenseEntity
import com.example.appdegestindegastos.domain.repository.TransactionRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCategoriesWithExpensesUseCaseTest {

    private lateinit var transactionRepository: TransactionRepository
    private lateinit var getCategoriesWithExpensesUseCase: GetCategoriesWithExpensesUseCase

    @Before
    fun setUp() {
        transactionRepository = mockk()
        getCategoriesWithExpensesUseCase = GetCategoriesWithExpensesUseCase(transactionRepository)
    }

    @Test
    fun `invoke should return mapped domain models from repository entities`() = runTest {
        // Arrange
        val categoryEntity = CategoryEntity(id = 1, type = "Food")
        val expenseEntity = ExpenseEntity(id = 10, amount = 12.50, description = "Lunch", date = 12345L, categoryId = 1)
        val entityList = listOf(
            CategoryWithExpensesEntity(
                category = categoryEntity,
                expenses = listOf(expenseEntity)
            )
        )
        every { transactionRepository.getCategoriesWithExpenses() } returns flowOf(entityList)

        // Act
        val resultFlow = getCategoriesWithExpensesUseCase()
        val resultList = resultFlow.first()

        // Assert
        assertEquals(1, resultList.size)
        val domainModel = resultList[0]

        assertEquals(categoryEntity.id, domainModel.category.id)
        assertEquals(categoryEntity.type, domainModel.category.type)

        assertEquals(1, domainModel.expenses.size)
        val domainExpense = domainModel.expenses[0]

        assertEquals(expenseEntity.id, domainExpense.id)
        assertEquals(expenseEntity.amount, domainExpense.amount, 0.0)
        assertEquals(expenseEntity.description, domainExpense.description)
        assertEquals(expenseEntity.date, domainExpense.date)
        assertEquals(expenseEntity.categoryId, domainExpense.categoryId)
    }
}