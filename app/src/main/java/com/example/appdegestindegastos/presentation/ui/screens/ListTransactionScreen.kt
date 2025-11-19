package com.example.appdegestindegastos.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.data.model.ExpenseEntity
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel
import java.time.Instant
import java.time.ZoneId

@Composable
fun ListTransactionScreen(navController: NavController, viewModel: TransactionViewModel) {
    val expensesUnsorted by viewModel.allExpenses.collectAsStateWithLifecycle()
    val expenses = expensesUnsorted.sortedBy { it.date }

    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium))) {
            items(expenses) { expense ->
                ExpenseItem(expense = expense) {
                    navController.navigate("expense_detail/${expense.id}")
                }
            }
        }
    }
}

@Composable
fun ExpenseItem(expense: ExpenseEntity, onClick: () -> Unit) {
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
                    text = Instant
                        .ofEpochMilli(expense.date)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate().toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(text = "$${expense.amount}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}