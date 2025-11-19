package com.example.appdegestindegastos.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel
import java.time.Instant
import java.time.ZoneId

@Composable
fun ExpenseDetailScreen(
    navController: NavController,
    expenseId: Long?,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val expenses by viewModel.allExpenses.collectAsStateWithLifecycle()
    val expense = expenses.find { it.id == expenseId }

    if (expense == null) {
        Text(text = stringResource(id = R.string.expense_not_found_message))
        return
    }

    var description by remember { mutableStateOf(expense.description) }
    var amount by remember { mutableStateOf(expense.amount.toString()) }
    var date by remember {
        mutableStateOf(
            Instant
                .ofEpochMilli(expense.date)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        )
    }
    val context = LocalContext.current

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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                val updatedExpense = expense.copy(
                    description = description,
                    amount = amount.toDouble(),
                    date = date.atStartOfDay(
                        ZoneId.systemDefault()
                    ).toInstant().toEpochMilli()
                )
                viewModel.updateExpense(updatedExpense)
                Toast.makeText(
                    context,
                    context.getString(R.string.expense_added_successfully),
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.save_action))
        }
    }
}
