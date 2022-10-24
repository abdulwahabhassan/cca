package com.smartflowtech.cupidcustomerapp.ui.presentation.settings

import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class SettingScreenUiState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: Any? = null
)
