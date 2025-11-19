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
    getCategoriesWithExpensesUseCase: GetCategoriesWithExpensesUseCase,
    getAllExpensesUseCase: GetAllExpensesUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase
) : ViewModel() {

    /**
     * Exposes a stream of categories with their associated expenses.
     * This StateFlow is collected by the UI to display a complete view of the data,
     * automatically updating when underlying data changes in the database.
     * The `stateIn` operator converts the cold Flow from the use case into a hot StateFlow,
     * allowing multiple collectors to share the same data stream efficiently.
     */
    val categoriesWithExpenses = getCategoriesWithExpensesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Exposes a stream of all expenses, unsorted.
     * This provides a raw, comprehensive list of all expense entries, suitable for screens
     * that need to perform their own client-side sorting or filtering.
     */
    val allExpenses = getAllExpensesUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * Handles the business logic for inserting a new expense.
     * This function is called from the UI and delegates the insertion operation
     * to the corresponding use case, abstracting the data layer from the presentation layer.
     * @param amount The monetary value of the expense.
     * @param description A textual description of the expense.
     * @param categoryId The foreign key linking the expense to its category.
     * @param date The timestamp of when the expense occurred.
     */
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

    /**
     * Handles the business logic for inserting a new category.
     * It launches a coroutine to perform the database operation asynchronously,
     * ensuring the UI remains responsive.
     * @param type The name or type of the category.
     */
    fun insertCategory(type: String) {
        viewModelScope.launch {
            insertCategoryUseCase(
                CategoryEntity(
                    type = type
                )
            )
        }
    }

    /**
     * Handles the business logic for updating an existing expense.
     * This function abstracts the update operation, allowing the UI to simply pass
     * the modified entity without needing to know the implementation details.
     * @param expense The ExpenseEntity with updated information.
     */
    fun updateExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            updateExpenseUseCase(expense)
        }
    }

    /**
     * Handles the business logic for updating an existing category.
     * Encapsulates the update logic, promoting separation of concerns.
     * @param category The CategoryEntity with updated information.
     */
    fun updateCategory(category: CategoryEntity) {
        viewModelScope.launch {
            updateCategoryUseCase(category)
        }
    }
}
