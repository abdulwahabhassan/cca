package com.smartflowtech.cupidcustomerapp.ui.presentation.password

import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class ChangePasswordScreenUiState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: Any? = null
)
