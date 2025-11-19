package com.example.appdegestindegastos.data.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.appdegestindegastos.data.model.CategoryEntity
import com.example.appdegestindegastos.data.model.ExpenseEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TransactionDaoTest {

    private lateinit var transactionDao: TransactionDao
    private lateinit var db: TransactionBd

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, TransactionBd::class.java
        ).build()
        transactionDao = db.transactionDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveCategory() = runTest {
        // Arrange
        val category = CategoryEntity(id = 1, type = "Groceries")

        // Act
        transactionDao.insertCategory(category)
        val allCategoriesWithExpenses = transactionDao.getCategoriesWithExpenses().first()
        val retrievedCategory = allCategoriesWithExpenses.first().category

        // Assert
        assertEquals(category, retrievedCategory)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveExpense() = runTest {
        // Arrange
        val category = CategoryEntity(id = 1, type = "Groceries")
        val expense = ExpenseEntity(id = 1, amount = 50.0, description = "Milk", date = System.currentTimeMillis(), categoryId = 1)

        // Act
        transactionDao.insertCategory(category)
        transactionDao.insertExpense(expense)
        val allExpenses = transactionDao.getAllExpenses().first()
        val retrievedExpense = allExpenses.first()

        // Assert
        assertEquals(expense, retrievedExpense)
    }

    @Test
    @Throws(Exception::class)
    fun getCategoriesWithExpenses_returnsCorrectlyStructuredData() = runTest {
        // Arrange
        val category1 = CategoryEntity(id = 1, type = "Food")
        val category2 = CategoryEntity(id = 2, type = "Transport")

        val expense1 = ExpenseEntity(id = 1, amount = 15.0, description = "Pizza", date = 1000L, categoryId = 1)
        val expense2 = ExpenseEntity(id = 2, amount = 25.0, description = "Tacos", date = 2000L, categoryId = 1)
        val expense3 = ExpenseEntity(id = 3, amount = 30.0, description = "Bus ticket", date = 3000L, categoryId = 2)

        // Act
        transactionDao.insertCategory(category1)
        transactionDao.insertCategory(category2)
        transactionDao.insertExpense(expense1)
        transactionDao.insertExpense(expense2)
        transactionDao.insertExpense(expense3)

        val categoriesWithExpenses = transactionDao.getCategoriesWithExpenses().first()

        // Assert
        assertEquals(2, categoriesWithExpenses.size)

        val foodCategory = categoriesWithExpenses.find { it.category.id.toInt() == 1 }
        val transportCategory = categoriesWithExpenses.find { it.category.id.toInt() == 2 }

        assertEquals("Food", foodCategory?.category?.type)
        assertEquals(2, foodCategory?.expenses?.size)
        assertTrue(foodCategory?.expenses?.containsAll(listOf(expense1, expense2)) ?: false)

        assertEquals("Transport", transportCategory?.category?.type)
        assertEquals(1, transportCategory?.expenses?.size)
        assertEquals(expense3, transportCategory?.expenses?.first())
    }
}
