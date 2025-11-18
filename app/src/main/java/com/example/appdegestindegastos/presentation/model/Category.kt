package com.example.appdegestindegastos.presentation.model

data class Category(
    val id: Int,
    val type: String,
    val expenses: List<Expense>
)