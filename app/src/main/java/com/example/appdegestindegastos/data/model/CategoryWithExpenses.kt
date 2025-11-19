package com.example.appdegestindegastos.data.model

import androidx.room.Embedded
import androidx.room.Relation

data class CategoryWithExpenses(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val expenses: List<ExpenseEntity>
)
