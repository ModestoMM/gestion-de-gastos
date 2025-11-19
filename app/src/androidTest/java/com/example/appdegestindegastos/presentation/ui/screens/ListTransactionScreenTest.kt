package com.example.appdegestindegastos.presentation.ui.screens

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appdegestindegastos.domain.model.Expense
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.Instant
import java.time.ZoneId

@RunWith(AndroidJUnit4::class)
class ListTransactionScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private lateinit var mockViewModel: TransactionViewModel

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        navController = TestNavHostController(context)
        navController.navigatorProvider.addNavigator(ComposeNavigator())
        mockViewModel = mockk(relaxed = true)
    }

    @Test
    fun listTransactionScreen_displaysExpensesCorrectly() {
        // Arrange
        val expenses = listOf(
            Expense(id = 1, amount = 10.0, description = "Coffee", date = System.currentTimeMillis(), categoryId = 1),
            Expense(id = 2, amount = 25.5, description = "Lunch", date = System.currentTimeMillis(), categoryId = 1)
        )
        val expensesFlow = MutableStateFlow(expenses)
        every { mockViewModel.allExpenses } returns expensesFlow

        // Act
        composeTestRule.setContent {
            NavHost(navController = navController, startDestination = "test_screen") {
                composable("test_screen") {
                    ListTransactionScreen(navController = navController, viewModel = mockViewModel)
                }
            }
        }

        // Assert
        composeTestRule.onNodeWithText("Coffee").assertIsDisplayed()
        composeTestRule.onNodeWithText("$10.0").assertIsDisplayed()

        composeTestRule.onNodeWithText("Lunch").assertIsDisplayed()
        composeTestRule.onNodeWithText("$25.5").assertIsDisplayed()
    }

    @Test
    fun listTransactionScreen_clickingOnExpenseNavigatesToDetail() {
        // Arrange
        val expenseToClick = Expense(id = 5, amount = 12.0, description = "Movie Ticket", date = System.currentTimeMillis(), categoryId = 2)
        val expenses = listOf(expenseToClick)
        val expensesFlow = MutableStateFlow(expenses)
        every { mockViewModel.allExpenses } returns expensesFlow

        // Act
        composeTestRule.setContent {
            NavHost(navController = navController, startDestination = "list_screen") {
                composable("list_screen") {
                    ListTransactionScreen(navController = navController, viewModel = mockViewModel)
                }
                composable("expense_detail/{id}") { } // Destino dummy para la navegación
            }
        }
        composeTestRule.onNodeWithText("Movie Ticket").performClick()

        // Assert
        val backStackEntry = navController.currentBackStackEntry
        assertEquals("expense_detail/{id}", backStackEntry?.destination?.route)
        assertEquals("5", backStackEntry?.arguments?.getString("id"))
    }

    @Test
    fun expenseItem_displaysDataCorrectly() {
        // Arrange
        val date = 1672531200000L // 2023-01-01
        val expense = Expense(id = 1, amount = 99.99, description = "Test Item", date = date, categoryId = 1)
        val expectedDateString = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate().toString()

        // Act
        composeTestRule.setContent {
            ExpenseItem(expense = expense, onClick = {})
        }

        // Assert
        composeTestRule.onNodeWithText("Test Item").assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedDateString).assertIsDisplayed()
        composeTestRule.onNodeWithText("$99.99").assertIsDisplayed()
    }
}
