package com.smartflowtech.cupidcustomerapp.ui.presentation.location

import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class LocationScreenUiState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: Any? = null
)
