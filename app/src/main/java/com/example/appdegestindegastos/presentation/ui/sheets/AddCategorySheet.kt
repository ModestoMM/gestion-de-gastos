package com.example.appdegestindegastos.presentation.ui.sheets

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel

import androidx.compose.ui.res.stringResource
import com.example.appdegestindegastos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategorySheet(viewModel: TransactionViewModel) {
    val sheetState = rememberModalBottomSheetState()
    var categoryName by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = { viewModel.onDismissSheet() },
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.add_new_category_title), style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = categoryName,
                onValueChange = { categoryName = it },
                label = { Text(stringResource(id = R.string.category_name_label)) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // TODO: Add logic to save the new category
                    viewModel.onDismissSheet()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.save_category_action))
            }
        }
    }
}