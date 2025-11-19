package com.example.appdegestindegastos.presentation.ui.sheets

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.presentation.ui.screens.DatePickerView
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseSheet(
    categories: List<CategoryEntity>,
    onAddExpense: (Double, String, Long, Long) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf<Long?>(if (categories.isEmpty()) 0L else categories.first().id) }
    var isDescriptionError by remember { mutableStateOf(false) }
    var isAmountError by remember { mutableStateOf(false) }
    var isCategoryError by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                stringResource(id = R.string.add_new_expense_title),
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = description,
                onValueChange = {
                    description = it
                    isDescriptionError = it.isBlank()
                },
                label = { Text(stringResource(id = R.string.description_label)) },
                isError = isDescriptionError,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = amount,
                onValueChange = {
                    amount = it
                    isAmountError = it.isBlank() || it.toDoubleOrNull() == null
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(stringResource(id = R.string.amount_label)) },
                isError = isAmountError,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Category Dropdown
            var expanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = categories.find { it.id == selectedCategoryId }?.type ?: "",
                    onValueChange = {
                        selectedCategoryId = categories.find { category -> category.type == it }?.id
                    },
                    readOnly = true,
                    label = { Text(stringResource(id = R.string.category_label)) },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = !expanded }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category.type) },
                            onClick = {
                                selectedCategoryId = category.id
                                isCategoryError = false
                                expanded = false
                            }
                        )
                    }
                }
            }
            if (isCategoryError) {
                Text(
                    text = stringResource(id = R.string.category_error_message),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }


            DatePickerView(selectedDate = selectedDate, onDateSelected = { selectedDate = it })

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    isDescriptionError = description.isBlank()
                    isAmountError = amount.isBlank() || amount.toDoubleOrNull() == null
                    isCategoryError = selectedCategoryId == null || selectedCategoryId == 0L

                    if (!isDescriptionError && !isAmountError && !isCategoryError) {
                        onAddExpense(
                            amount.toDouble(),
                            description,
                            selectedCategoryId ?: 0L,
                            selectedDate.atStartOfDay(
                                ZoneId.systemDefault()
                            ).toInstant().toEpochMilli()
                        )
                        Toast.makeText(
                            context,
                            context.getString(R.string.expense_added_successfully),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }, modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.save_expense_action))
            }
        }
    }
}