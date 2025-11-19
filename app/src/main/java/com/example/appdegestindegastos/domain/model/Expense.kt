package com.example.appdegestindegastos.domain.model

data class Expense(
    val id: Long = 0,
    val amount: Double,
    val description: String,
    val date: Long,
    val categoryId: Long
)
