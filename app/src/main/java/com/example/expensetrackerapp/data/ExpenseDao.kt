package com.example.expensetrackerapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Upsert
    suspend fun upsertExpense(expense: Expense)

    @Delete
    suspend fun deleteExpense(expense: Expense)

    @Query("SELECT * FROM expense ORDER BY dateAdded")
    fun getExpenseOrderByDateAdded() : Flow<List<Expense>>

    // Flow will update the UI whenever the database changer
    // it is asynchronous in nature
}