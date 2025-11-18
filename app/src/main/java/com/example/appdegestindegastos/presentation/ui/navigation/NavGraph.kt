package com.example.appdegestindegastos.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavHostController
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appdegestindegastos.presentation.ui.screens.CurrentMonthlyExpenseScreen
import com.example.appdegestindegastos.presentation.ui.screens.ExpensesByCategoryScreen
import com.example.appdegestindegastos.presentation.ui.screens.ListTransactionScreen
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel

import com.example.appdegestindegastos.presentation.ui.screens.ExpenseDetailScreen
import com.example.appdegestindegastos.presentation.ui.screens.CategoryDetailScreen
import androidx.navigation.NavType
import androidx.navigation.navArgument

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = BottomNavItem.CurrentMonthlyExpense.route) {
        composable(BottomNavItem.CurrentMonthlyExpense.route) {
            val viewModel: TransactionViewModel = hiltViewModel()
            CurrentMonthlyExpenseScreen(navController = navController, viewModel = viewModel)
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
            arguments = listOf(navArgument("categoryId") { type = NavType.IntType })
        ) { backStackEntry ->
            val categoryId = backStackEntry.arguments?.getInt("categoryId")
            CategoryDetailScreen(navController = navController, categoryId = categoryId)
        }
        composable(
            route = BottomNavItem.ExpenseDetail.route,
            arguments = listOf(navArgument("expenseId") { type = NavType.IntType })
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getInt("expenseId")
            ExpenseDetailScreen(navController = navController, expenseId = expenseId)
        }
    }
}
