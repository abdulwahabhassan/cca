package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.model.Wallet
import com.smartflowtech.cupidcustomerapp.model.response.TransactionsResponseData
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class HomeScreenUiState(
    val viewModelResult: ViewModelResult,
    val transactions: List<Transaction>,
    val message: String? = null,
    val wallets: List<Wallet>
)