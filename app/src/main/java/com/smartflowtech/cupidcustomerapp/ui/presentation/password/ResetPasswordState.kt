package com.smartflowtech.cupidcustomerapp.ui.presentation.password

import com.smartflowtech.cupidcustomerapp.model.response.VerifyEmailData
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class ResetPasswordState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: VerifyEmailData? = null
)
