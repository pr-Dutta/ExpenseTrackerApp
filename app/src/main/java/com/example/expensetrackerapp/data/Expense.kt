package com.example.expensetrackerapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expense(
    val title: String,
    val amount: Float,
    val category: String,
    val dateAdded: Long,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)