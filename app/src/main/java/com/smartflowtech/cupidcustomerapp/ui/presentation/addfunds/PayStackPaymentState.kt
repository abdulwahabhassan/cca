package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import com.smartflowtech.cupidcustomerapp.model.response.PayStackPaymentData
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class PayStackPaymentState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: PayStackPaymentData? = null
)
