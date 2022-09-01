package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.model.response.TransactionsResponseData
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class TransactionHistoryUiState(
    val viewModelResult: ViewModelResult,
    val data: List<Transaction>,
    val message: String? = null,
)