package com.example.appdegestindegastos.presentation.viewmodel

import app.cash.turbine.test
import com.example.appdegestindegastos.domain.model.Category
import com.example.appdegestindegastos.domain.model.CategoryWithExpense
import com.example.appdegestindegastos.domain.model.Expense
import com.example.appdegestindegastos.domain.usecase.GetAllExpensesUseCase
import com.example.appdegestindegastos.domain.usecase.GetCategoriesWithExpensesUseCase
import com.example.appdegestindegastos.domain.usecase.InsertCategoryUseCase
import com.example.appdegestindegastos.domain.usecase.InsertExpenseUseCase
import com.example.appdegestindegastos.domain.usecase.UpdateCategoryUseCase
import com.example.appdegestindegastos.domain.usecase.UpdateExpenseUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TransactionViewModelTest {

    // Dispatcher para controlar las corrutinas en las pruebas
    private val testDispatcher = StandardTestDispatcher()

    // Mocks para los casos de uso
    private lateinit var insertExpenseUseCase: InsertExpenseUseCase
    private lateinit var insertCategoryUseCase: InsertCategoryUseCase
    private lateinit var getCategoriesWithExpensesUseCase: GetCategoriesWithExpensesUseCase
    private lateinit var getAllExpensesUseCase: GetAllExpensesUseCase
    private lateinit var updateExpenseUseCase: UpdateExpenseUseCase
    private lateinit var updateCategoryUseCase: UpdateCategoryUseCase

    // La instancia del ViewModel que vamos a probar
    private lateinit var viewModel: TransactionViewModel

    @Before
    fun setUp() {
        // Establecemos el dispatcher de prueba como el principal
        Dispatchers.setMain(testDispatcher)

        // Inicializamos los mocks antes de cada prueba
        insertExpenseUseCase = mockk(relaxed = true)
        insertCategoryUseCase = mockk(relaxed = true)
        getCategoriesWithExpensesUseCase = mockk()
        getAllExpensesUseCase = mockk()
        updateExpenseUseCase = mockk(relaxed = true)
        updateCategoryUseCase = mockk(relaxed = true)

        // Pre-configuramos los mocks que devuelven Flows
        every { getAllExpensesUseCase() } returns flowOf(emptyList())
        every { getCategoriesWithExpensesUseCase() } returns flowOf(emptyList())

        // Creamos el ViewModel después de configurar los mocks
        viewModel = TransactionViewModel(
            insertExpenseUseCase,
            insertCategoryUseCase,
            getCategoriesWithExpensesUseCase,
            getAllExpensesUseCase,
            updateExpenseUseCase,
            updateCategoryUseCase
        )
    }

    @After
    fun tearDown() {
        // Reseteamos el dispatcher principal después de cada prueba
        Dispatchers.resetMain()
    }

    @Test
    fun `insertExpense should call InsertExpenseUseCase with correct expense`() = runTest {
        // Arrange
        val amount = 100.0
        val description = "Test Expense"
        val categoryId = 1L
        val date = System.currentTimeMillis()
        val expenseSlot = slot<Expense>()

        // Act
        viewModel.insertExpense(amount, description, categoryId, date)
        testDispatcher.scheduler.advanceUntilIdle() // Aseguramos que la corutina se complete

        // Assert
        coVerify { insertExpenseUseCase(capture(expenseSlot)) }
        assertEquals(amount, expenseSlot.captured.amount, 0.0)
        assertEquals(description, expenseSlot.captured.description)
        assertEquals(categoryId, expenseSlot.captured.categoryId)
        assertEquals(date, expenseSlot.captured.date)
    }

    @Test
    fun `insertCategory should call InsertCategoryUseCase with correct category`() = runTest {
        // Arrange
        val type = "Groceries"
        val categorySlot = slot<Category>()

        // Act
        viewModel.insertCategory(type)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { insertCategoryUseCase(capture(categorySlot)) }
        assertEquals(type, categorySlot.captured.type)
    }

    @Test
    fun `updateExpense should call UpdateExpenseUseCase`() = runTest {
        // Arrange
        val expense = Expense(id = 1, amount = 150.0, description = "Updated Expense", categoryId = 1, date = 0)

        // Act
        viewModel.updateExpense(expense)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { updateExpenseUseCase(expense) }
    }

    @Test
    fun `updateCategory should call UpdateCategoryUseCase`() = runTest {
        // Arrange
        val category = Category(id = 1, type = "Utilities")

        // Act
        viewModel.updateCategory(category)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { updateCategoryUseCase(category) }
    }

    @Test
    fun `allExpenses should emit values from GetAllExpensesUseCase`() = runTest {
        // Arrange
        val expenses = listOf(Expense(id = 1, amount = 50.0, description = "Lunch", categoryId = 2, date = 0))
        every { getAllExpensesUseCase() } returns flowOf(expenses)

        // Re-creamos el viewModel para que recoja el nuevo flow
        setupViewModel()

        // Act & Assert
        viewModel.allExpenses.test {
            // La primera emisión será la lista vacía inicial
            assertEquals(emptyList<Expense>(), awaitItem())
            // Avanzamos el despachador para permitir que la nueva emisión llegue
            testDispatcher.scheduler.advanceUntilIdle()
            // Ahora deberíamos recibir la lista de gastos
            assertEquals(expenses, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `categoriesWithExpenses should emit values from GetCategoriesWithExpensesUseCase`() = runTest {
        // Arrange
        val categoriesWithExpenses = listOf(
            CategoryWithExpense(
                category = Category(id = 1, type = "Food"),
                expenses = listOf(Expense(id = 1, amount = 25.0, description = "Dinner", categoryId = 1, date = 0))
            )
        )
        every { getCategoriesWithExpensesUseCase() } returns flowOf(categoriesWithExpenses)

        // Re-creamos el viewModel para que recoja el nuevo flow
        setupViewModel()

        // Act & Assert
        viewModel.categoriesWithExpenses.test {
            assertEquals(emptyList<CategoryWithExpense>(), awaitItem())
            testDispatcher.scheduler.advanceUntilIdle()
            assertEquals(categoriesWithExpenses, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun setupViewModel() {
        viewModel = TransactionViewModel(
            insertExpenseUseCase,
            insertCategoryUseCase,
            getCategoriesWithExpensesUseCase,
            getAllExpensesUseCase,
            updateExpenseUseCase,
            updateCategoryUseCase
        )
    }
}