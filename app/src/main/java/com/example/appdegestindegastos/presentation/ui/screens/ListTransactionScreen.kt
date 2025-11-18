package com.example.appdegestindegastos.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.presentation.model.Expense
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ListTransactionScreen(navController: NavController, viewModel: TransactionViewModel) {
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium))) {
                items(expenses) { expense ->
                    ExpenseItem(expense = expense) {
                        navController.navigate("expense_detail/${expense.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: Expense, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_elevation))
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_large))
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = expense.description, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = expense.date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(text = "$${expense.amount}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}