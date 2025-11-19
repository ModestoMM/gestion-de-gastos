package com.example.appdegestindegastos.presentation.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.appdegestindegastos.R
import com.example.appdegestindegastos.presentation.viewmodel.TransactionViewModel

@Composable
fun ExpensesByCategoryScreen(navController: NavController, viewModel: TransactionViewModel) {
    val categoriesWithExpenses by viewModel.categoriesWithExpenses.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf("") }

    val filteredCategories = categoriesWithExpenses.filter {
        it.category.type.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(stringResource(id = R.string.search_category_placeholder)) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_large)))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.spacing_medium))) {
            items(filteredCategories) { categoryWithExpenses ->
                CategoryItem(categoryWithExpenses = categoryWithExpenses) {
                    navController.navigate("category_detail/${categoryWithExpenses.category.id}")
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    categoryWithExpenses: com.example.appdegestindegastos.data.model.CategoryWithExpenses,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(id = R.dimen.card_elevation))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))) {
            Text(
                text = categoryWithExpenses.category.type,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_small)))
            Text(
                text = "${stringResource(id = R.string.total_label)} $${categoryWithExpenses.expenses.sumOf { it.amount }}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
