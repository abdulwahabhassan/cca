package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import co.paystack.android.model.Card
import com.smartflowtech.cupidcustomerapp.model.response.PayStackPaymentRequestData
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class PayStackPaymentState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: PayStackPaymentRequestData? = null
)
