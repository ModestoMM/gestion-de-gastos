package com.example.appdegestindegastos.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.presentation.ui.sheets.AddCategorySheet
import com.example.appdegestindegastos.presentation.ui.sheets.AddExpenseSheet
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CurrentMonthlyExpenseScreen(viewModel: TransactionViewModel) {
    val expenses by viewModel.allExpenses.collectAsStateWithLifecycle()
    val categoriesWithExpenses by viewModel.categoriesWithExpenses.collectAsStateWithLifecycle()
    var showAddExpenseSheet by remember { mutableStateOf(false) }
    var showAddCategorySheet by remember { mutableStateOf(false) }

    val currentMonth = LocalDate.now().monthValue
    val currentYear = LocalDate.now().year

    val totalForMonth = expenses.filter {
        val calendar = Calendar.getInstance()
        calendar.time = Date(it.date)
        calendar.get(Calendar.MONTH) + 1 == currentMonth && calendar.get(Calendar.YEAR) == currentYear
    }.sumOf { it.amount }

    if (showAddExpenseSheet) {
        AddExpenseSheet(
            categories = categoriesWithExpenses.map { it.category },
            onAddExpense = { amount, description, categoryId, date ->
                viewModel.insertExpense(amount, description, categoryId, date)
                showAddExpenseSheet = false
            },
            onDismiss = { showAddExpenseSheet = false }
        )
    }

    if (showAddCategorySheet) {
        AddCategorySheet(
            onAddCategory = { type ->
                viewModel.insertCategory(type)
                showAddCategorySheet = false
            },
            onDismiss = { showAddCategorySheet = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_large))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { showAddCategorySheet = true }) {
                Text(text = stringResource(id = R.string.add_category_action))
            }
            Button(onClick = { showAddExpenseSheet = true }) {
                Text(text = stringResource(id = R.string.add_expense_action))
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        Text(
            text = stringResource(id = R.string.monthly_summary_title),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

        TotalExpensesCard(total = totalForMonth)
    }
}

@Composable
fun TotalExpensesCard(total: Double) {
    val locale = Locale.Builder().setLanguage("es").setRegion("MX").build()
    val currencyFormat = NumberFormat.getCurrencyInstance(locale)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.total_expenses_card_height)),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_elevation_high)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.total_month_expenses_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
                Text(
                    text = currencyFormat.format(total),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}
