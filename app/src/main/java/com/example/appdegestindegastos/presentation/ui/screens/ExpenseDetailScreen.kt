package com.example.appdegestindegastos.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel

@Composable
fun ExpenseDetailScreen(
    navController: NavController,
    expenseId: Int?,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val expenses by viewModel.expenses.collectAsStateWithLifecycle()
    val expense = expenses.find { it.id == expenseId }

    if (expense == null) {
        Text(text = stringResource(id = R.string.expense_not_found_message))
        return
    }

    var description by remember { mutableStateOf(expense.description) }
    var amount by remember { mutableStateOf(expense.amount.toString()) }
    var date by remember { mutableStateOf(expense.date) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_large))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_action)
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacing_medium)))
            Text(
                text = stringResource(id = R.string.edit_expense_title),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text(stringResource(id = R.string.description_label)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_medium)))
        TextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(stringResource(id = R.string.amount_label)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

        // Date Picker
        DatePickerView(
            selectedDate = date,
            onDateSelected = { newDate -> date = newDate }
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                // TODO: Add logic to save the changes in the ViewModel
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.save_action))
        }
    }
}
