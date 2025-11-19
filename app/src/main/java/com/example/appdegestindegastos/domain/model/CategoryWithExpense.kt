package com.example.appdegestindegastos.domain.model

data class CategoryWithExpense (
    val category: Category,
    val expenses: List<Expense>
)