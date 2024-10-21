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
    fun setExpenseOrderByDateAdded() : Flow<List<Expense>>
    // In Kotlin, Flow is a reactive streams API from the
    // kotlinx.coroutines library that allows handling asynchronous
    // data streams in a sequential manner. It is part of Kotlin's
    // coroutines framework and is similar to other reactive streams
    // like RxJava but designed to work natively with Kotlin coroutines.
}