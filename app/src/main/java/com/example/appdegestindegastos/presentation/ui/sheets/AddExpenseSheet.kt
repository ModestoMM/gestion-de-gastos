package com.example.appdegestindegastos.presentation.ui.sheets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel

import com.example.appdegestindegastos.presentation.ui.screens.DatePickerView
import java.time.LocalDate

import androidx.compose.ui.res.stringResource
import com.example.appdegestindegastos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseSheet(viewModel: TransactionViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    // Aquí podrías añadir un selector de categoría

    ModalBottomSheet(
        onDismissRequest = { viewModel.onDismissSheet() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.add_new_expense_title), style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(id = R.string.description_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text(stringResource(id = R.string.amount_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            DatePickerView(selectedDate = selectedDate, onDateSelected = { selectedDate = it })

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // TODO: Add logic to save the new expense
                    viewModel.onDismissSheet()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.save_expense_action))
            }
        }
    }
}