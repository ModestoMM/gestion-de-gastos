package com.example.appdegestindegastos.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.data.model.CategoryWithExpenses
import com.example.appdegestindegastos.data.model.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    /**
     * Inserts a new expense into the database. If the expense already exists, it is replaced.
     * @param expense The expense to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    /**
     * Inserts a new category into the database. If the category already exists, it is replaced.
     * @param category The category to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity)

    /**
     * Updates an existing expense in the database.
     * @param expense The expense to be updated.
     */
    @Update
    suspend fun updateExpense(expense: ExpenseEntity)

    /**
     * Updates an existing category in the database.
     * @param category The category to be updated.
     */
    @Update
    suspend fun updateCategory(category: CategoryEntity)

    /**
     * Returns a flow of the list of categories with their associated expenses.
     * The list is automatically updated when the data in the database changes.
     */
    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithExpenses(): Flow<List<CategoryWithExpenses>>

    /**
     * Returns a flow of the list of all expenses.
     * The list is automatically updated when the data in the database changes.
     */
    @Transaction
    @Query("SELECT * FROM expenses")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    /**
     * Returns a flow of the list of all expenses ordered by date in descending order.
     * The list is automatically updated when the data in the database changes.
     */
    @Transaction
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpensesOrderByDate(): Flow<List<ExpenseEntity>>
}
