package com.example.appdegestindegastos.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.presentation.model.Category
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel

@Composable
fun ExpensesByCategoryScreen(navController: NavController, viewModel: TransactionViewModel) {
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }

    val filteredCategories = categories.filter {
        it.type.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(stringResource(id = R.string.search_category_placeholder)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.fillMaxWidth())
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium))) {
                items(filteredCategories) { category ->
                    CategoryItem(category = category) {
                        navController.navigate("category_detail/${category.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_elevation))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
            Text(text = category.type, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
            Text(text = "${stringResource(id = R.string.total_label)} $${category.expenses.sumOf { it.amount }}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
