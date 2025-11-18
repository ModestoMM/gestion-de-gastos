package com.example.appdegestindegastos.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.presentation.ui.sheets.AddCategorySheet
import com.example.appdegestindegastos.presentation.ui.sheets.AddExpenseSheet
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Locale

@Composable
fun CurrentMonthlyExpenseScreen(navController: NavController, viewModel: TransactionViewModel) {
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()
    val showAddExpenseSheet by viewModel.showAddExpenseSheet.collectAsStateWithLifecycle()
    val showAddCategorySheet by viewModel.showAddCategorySheet.collectAsStateWithLifecycle()

    val currentMonth = LocalDate.now().month
    val currentYear = LocalDate.now().year

    val totalForMonth = expenses.filter {
        it.date.month == currentMonth && it.date.year == currentYear
    }.sumOf { it.amount }

    if (showAddExpenseSheet) {
        AddExpenseSheet(viewModel = viewModel)
    }

    if (showAddCategorySheet) {
        AddCategorySheet(viewModel = viewModel)
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
            Button(onClick = { viewModel.onAddCategoryClicked() }) {
                Text(text = stringResource(id = R.string.add_category_action))
            }
            Button(onClick = { viewModel.onAddExpenseClicked() }) {
                Text(text = stringResource(id = R.string.add_expense_action))
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_xlarge)))
        Text(text = stringResource(id = R.string.monthly_summary_title), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))
        // Aquí se mostraría el resumen mensual de los gastos

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))

        TotalExpensesCard(total = totalForMonth)
    }
}

@Composable
fun TotalExpensesCard(total: Double) {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "MX"))

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
