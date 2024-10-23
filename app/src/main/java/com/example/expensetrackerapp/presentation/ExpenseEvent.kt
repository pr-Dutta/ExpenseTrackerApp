package com.example.expensetrackerapp.presentation

import com.example.expensetrackerapp.data.Expense

sealed interface ExpenseEvent {
    object SortExpense: ExpenseEvent
    data class DeleteExpense(val expense: Expense): ExpenseEvent
    data class SaveExpense(
        val title: String,
        val amount: Int,
        val category: String
    ): ExpenseEvent
}