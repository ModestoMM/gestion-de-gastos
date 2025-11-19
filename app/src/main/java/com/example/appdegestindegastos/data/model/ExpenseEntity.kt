package com.example.appdegestindegastos.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val amount: Double,
    val description: String,
    val date: Long,
    val categoryId: Long
)
