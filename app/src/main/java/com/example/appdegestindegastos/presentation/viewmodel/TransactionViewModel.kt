package com.example.appdegestindegastos.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.data.model.ExpenseEntity
import com.example.appdegestindegastos.domain.usecase.GetAllExpensesUseCase
import com.example.appdegestindegastos.domain.usecase.GetCategoriesWithExpensesUseCase
import com.example.appdegestindegastos.domain.usecase.InsertCategoryUseCase
import com.example.appdegestindegastos.domain.usecase.InsertExpenseUseCase
import com.example.appdegestindegastos.domain.usecase.UpdateCategoryUseCase
import com.example.appdegestindegastos.domain.usecase.UpdateExpenseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val insertExpenseUseCase: InsertExpenseUseCase,
    private val insertCategoryUseCase: InsertCategoryUseCase,
    private val getCategoriesWithExpensesUseCase: GetCategoriesWithExpensesUseCase,
    private val getAllExpensesUseCase: GetAllExpensesUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase
) : ViewModel() {

    val categoriesWithExpenses = getCategoriesWithExpensesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allExpenses = getAllExpensesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertExpense(amount: Double, description: String, categoryId: Long, date: Long) {
        viewModelScope.launch {
            insertExpenseUseCase(
                ExpenseEntity(
                    amount = amount,
                    description = description,
                    date = date,
                    categoryId = categoryId
                )
            )
        }
    }

    fun insertCategory(type: String) {
        viewModelScope.launch {
            insertCategoryUseCase(
                CategoryEntity(
                    type = type
                )
            )
        }
    }

    fun updateExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            updateExpenseUseCase(expense)
        }
    }

    fun updateCategory(category: CategoryEntity) {
        viewModelScope.launch {
            updateCategoryUseCase(category)
        }
    }
}
