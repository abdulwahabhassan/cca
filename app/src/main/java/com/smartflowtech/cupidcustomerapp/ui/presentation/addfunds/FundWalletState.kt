package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import com.smartflowtech.cupidcustomerapp.model.response.FundWalletData
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class FundWalletState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: FundWalletData? = null
)
