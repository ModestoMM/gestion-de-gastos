package com.example.appdegestindegastos.data.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * A relational data class that represents a one-to-many relationship between a [CategoryEntity]
 * and a list of [ExpenseEntity] objects.
 * This class is used by Room to execute transactional queries that fetch a category and all of
 * its associated expenses in a single, efficient operation.
 *
 * @property category The parent [CategoryEntity]. The `@Embedded` annotation allows Room to treat
 *           the fields of this object as if they were columns in the main query result.
 * @property expenses A list of all child [ExpenseEntity] objects. The `@Relation` annotation
 *            defines the link between the parent and child entities, specifying the parent column
 *            (`id` from `CategoryEntity`) and the child column (`categoryId` from `ExpenseEntity`).
 */
data class CategoryWithExpenses(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId"
    )
    val expenses: List<ExpenseEntity>
)
