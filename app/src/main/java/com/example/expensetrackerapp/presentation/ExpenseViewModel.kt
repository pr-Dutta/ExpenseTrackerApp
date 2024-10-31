package com.example.expensetrackerapp.presentation

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetrackerapp.data.Expense
import com.example.expensetrackerapp.data.ExpenseDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// - (30-10-2024)
class ExpenseViewModel(
    private val dao: ExpenseDao
) : ViewModel() {

    private val isSortedByDateAdded = MutableStateFlow(true)
    private val expenses = isSortedByDateAdded.flatMapLatest { sort ->
        if (sort) {
            dao.getExpenseOrderByDateAdded()
        }else {
            dao.getExpenseOrderByDateAdded()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(ExpenseState())
    val state =
        combine(_state, isSortedByDateAdded, expenses) { state, isSortedByDateAdded, expenses ->
            state.copy(
                expenseList = expenses
            )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExpenseState())

    fun onEvent(event: ExpenseEvent) {
        when (event) {
            is ExpenseEvent.DeleteExpense -> {
                viewModelScope.launch {
                    dao.deleteExpense(event.expense)
                }
            }

            is ExpenseEvent.SaveExpense -> {
                val expense = Expense(
                    title = state.value.title.value,
                    amount = state.value.amount.value.toFloat(),
                    category = state.value.category.value,
                    dateAdded = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    dao.upsertExpense(expense)
                }

                _state.update {
                    it.copy(
                        title = mutableStateOf(""),
                        amount = mutableIntStateOf(0),
                        category = mutableStateOf("")
                    )
                }
            }

            ExpenseEvent.SortExpense -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }
        }
    }
}











