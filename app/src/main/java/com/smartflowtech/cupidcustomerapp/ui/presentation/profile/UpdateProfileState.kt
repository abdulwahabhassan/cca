package com.smartflowtech.cupidcustomerapp.ui.presentation.profile

import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class UpdateProfileState(
    val viewModelResult: ViewModelResult,
    val message: String? = null,
    val data: Any? = null
)
