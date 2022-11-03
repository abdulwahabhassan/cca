package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import com.smartflowtech.cupidcustomerapp.model.domain.Transaction
import com.smartflowtech.cupidcustomerapp.model.domain.Wallet
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class HomeScreenUiState(
    val viewModelResult: ViewModelResult,
    val transactions: List<Transaction> = emptyList(),
    val message: String? = null,
    val wallets: List<Wallet> = emptyList()
)