package com.example.expensetrackerapp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Expense::class],
    version = 1
)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract val dao: ExpenseDao
}

// Learn Flow,
// Learn why we are using abstract class here,
// Why we are using interface for Dao.