package com.smartflowtech.cupidcustomerapp.ui.presentation.station

import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class StationsScreenUiState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: Any? = null
)
