package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class PrintTransactionReportState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
)
