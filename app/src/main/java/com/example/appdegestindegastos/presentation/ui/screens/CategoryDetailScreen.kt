package com.example.appdegestindegastos.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel

@Composable
fun CategoryDetailScreen(
    navController: NavController,
    categoryId: Int?,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val categories by viewModel.categories.collectAsState()
    val category = categories.find { it.id == categoryId }

    if (category == null) {
        Text(text = stringResource(id = R.string.category_not_found_message))
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_action)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.edit_category_title),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = category.type,
            onValueChange = { /* TODO: Handle change */ },
            label = { Text(stringResource(id = R.string.category_name_label)) }
        )
        // Aquí podrías listar los gastos de esta categoría también
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text(text = stringResource(id = R.string.save_action))
        }
    }
}
