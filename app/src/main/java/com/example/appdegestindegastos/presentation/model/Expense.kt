package com.example.appdegestindegastos.presentation.model

import java.time.LocalDate

data class Expense(
    val id: Int,
    val amount: Double,
    val description: String,
    val date: LocalDate,
    val categoryId: Int
)