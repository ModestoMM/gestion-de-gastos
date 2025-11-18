package com.example.appdegestindegastos.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.appdegestindegastos.R

sealed class BottomNavItem(val route: String, val title: Int, val icon: ImageVector) {

    companion object {
        const val CURRENT_MONTHLY_EXPENSE_ROUTE = "monthlySummary"
        const val EXPENSES_BY_CATEGORY = "expensesByCategory"
        const val LIST_TRANSACTION = "listTransaction"
    }

    object CurrentMonthlyExpense : BottomNavItem(
        CURRENT_MONTHLY_EXPENSE_ROUTE,
        R.string.current_monthly_expense_title_old,
        Icons.Default.DateRange
    )

    object ExpensesByCategory : BottomNavItem(
        EXPENSES_BY_CATEGORY,
        R.string.expenses_by_category_old,
        Icons.Filled.Info
    )

    object ListTransaction : BottomNavItem(
        LIST_TRANSACTION,
        R.string.list_transaction_old,
        Icons.AutoMirrored.Filled.List
    )

    object CategoryDetail : BottomNavItem(
        "category_detail/{categoryId}",
        R.string.category_detail,
        Icons.Default.Edit
    )

    object ExpenseDetail : BottomNavItem(
        "expense_detail/{expenseId}",
        R.string.expense_detail,
        Icons.Default.Edit
    )
}