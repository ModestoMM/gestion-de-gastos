package com.example.appdegestindegastos.presentation.ui.screens

import android.widget.Toast
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel

@Composable
fun CategoryDetailScreen(
    navController: NavController,
    categoryId: Long?,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val categoriesWithExpenses by viewModel.categoriesWithExpenses.collectAsState()
    val categoryWithExpenses = categoriesWithExpenses.find { it.category.id == categoryId }
    val context = LocalContext.current

    if (categoryWithExpenses == null) {
        Text(text = stringResource(id = R.string.category_not_found_message))
        return
    }

    var categoryName by remember { mutableStateOf(categoryWithExpenses.category.type) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.spacing_large))
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_action)
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(R.dimen.spacing_medium)))
            Text(
                text = stringResource(id = R.string.edit_category_title),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))
        TextField(
            value = categoryName,
            onValueChange = { categoryName = it },
            label = { Text(stringResource(id = R.string.category_name_label)) }
        )
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacing_large)))
        Button(onClick = {
            val updatedCategory = categoryWithExpenses.category.copy(type = categoryName)
            viewModel.updateCategory(updatedCategory)
            Toast.makeText(
                context,
                context.getString(R.string.category_updated_successfully),
                Toast.LENGTH_SHORT
            ).show()
            navController.popBackStack()
        }) {
            Text(text = stringResource(id = R.string.save_action))
        }
    }
}
