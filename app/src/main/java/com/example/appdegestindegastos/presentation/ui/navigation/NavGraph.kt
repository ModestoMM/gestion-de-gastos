package com.example.appdegestindegastos.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.appdegestindegastos.presentation.ui.screens.CategoryDetailScreen
import com.example.appdegestindegastos.presentation.ui.screens.CurrentMonthlyExpenseScreen
import com.example.appdegestindegastos.presentation.ui.screens.ExpenseDetailScreen
import com.example.appdegestindegastos.presentation.ui.screens.ExpensesByCategoryScreen
import com.example.appdegestindegastos.presentation.ui.screens.ListTransactionScreen
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.CurrentMonthlyExpense.route
    ) {
        composable(BottomNavItem.CurrentMonthlyExpense.route) {
            val viewModel: TransactionViewModel = hiltViewModel()
            CurrentMonthlyExpenseScreen(viewModel = viewModel)
        }
        composable(BottomNavItem.ExpensesByCategory.route) {
            val viewModel: TransactionViewModel = hiltViewModel()
            ExpensesByCategoryScreen(navController = navController, viewModel = viewModel)
        }
        composable(BottomNavItem.ListTransaction.route) {
            val viewModel: TransactionViewModel = hiltViewModel()
            ListTransactionScreen(navController = navController, viewModel = viewModel)
        }
        composable(
            route = BottomNavItem.CategoryDetail.route,
            arguments = listOf(navArgument("categoryId") { type = NavType.LongType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getLong("categoryId")
            CategoryDetailScreen(navController = navController, categoryId = categoryId)
        }
        composable(
            route = BottomNavItem.ExpenseDetail.route,
            arguments = listOf(navArgument("expenseId") { type = NavType.LongType })
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getLong("expenseId")
            ExpenseDetailScreen(navController = navController, expenseId = expenseId)
        }
    }
}
