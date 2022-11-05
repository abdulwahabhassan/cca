package com.smartflowtech.cupidcustomerapp.ui.presentation.login

import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult

data class LoginState (
    val viewModelResult: ViewModelResult,
    val message: String? = null,
        )