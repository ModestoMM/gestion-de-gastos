package com.example.appdegestindegastos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdegestindegastos.presentation.model.Category
import com.example.appdegestindegastos.presentation.model.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor() : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories.asStateFlow()

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _showAddExpenseSheet = MutableStateFlow(false)
    val showAddExpenseSheet: StateFlow<Boolean> = _showAddExpenseSheet.asStateFlow()

    private val _showAddCategorySheet = MutableStateFlow(false)
    val showAddCategorySheet: StateFlow<Boolean> = _showAddCategorySheet.asStateFlow()

    init {
        loadDummyData()
    }

    fun onAddExpenseClicked() {
        _showAddExpenseSheet.value = true
    }

    fun onAddCategoryClicked() {
        _showAddCategorySheet.value = true
    }

    fun onDismissSheet() {
        _showAddExpenseSheet.value = false
        _showAddCategorySheet.value = false
    }

    private fun loadDummyData() {
        viewModelScope.launch {
            _isLoading.value = true
            // Dummy Expenses
            val expense1 = Expense(
                id = 1,
                amount = 75.50,
                description = "Compra semanal",
                date = LocalDate.now(),
                categoryId = 1
            )
            val expense2 = Expense(
                id = 2,
                amount = 12.00,
                description = "Café",
                date = LocalDate.now().minusDays(1),
                categoryId = 1
            )
            val expense3 = Expense(
                id = 3,
                amount = 200.0,
                description = "Gasolina",
                date = LocalDate.now().minusDays(2),
                categoryId = 2
            )
            val expense4 = Expense(
                id = 4,
                amount = 50.0,
                description = "Cine",
                date = LocalDate.now().minusDays(3),
                categoryId = 3
            )
            val allExpenses = listOf(expense1, expense2, expense3, expense4)
            _expenses.value = allExpenses

            // Dummy Categories
            val category1 = Category(id = 1, type = "Comida", expenses = listOf(expense1, expense2))
            val category2 = Category(id = 2, type = "Transporte", expenses = listOf(expense3))
            val category3 = Category(id = 3, type = "Ocio", expenses = listOf(expense4))
            _categories.value = listOf(category1, category2, category3)

            _isLoading.value = false
        }
    }
}