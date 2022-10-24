package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class VerifyPayStackPaymentState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: Any? = null
)
