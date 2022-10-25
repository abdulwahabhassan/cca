package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.PasswordRepository
import com.smartflowtech.cupidcustomerapp.model.request.VerifyEmailRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.ResetPasswordScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository,
    private val dataStorePrefsRepository: DataStorePrefsRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var resetPasswordScreenUiState by mutableStateOf(ResetPasswordScreenUiState(ViewModelResult.INITIAL))
        private set


    fun forgotPasswordVerifyEmail(email: String) {
        viewModelScope.launch {

            resetPasswordScreenUiState =
                ResetPasswordScreenUiState(viewModelResult = ViewModelResult.LOADING)

            when (val repositoryResult = passwordRepository.forgotPasswordVerifyEmail(
                token = appConfigPreferences.token,
                VerifyEmailRequestBody(
                    hostname = "",
                    type = "",
                    email = email
                )
            )) {
                is RepositoryResult.Success -> {
                    repositoryResult.data?.let { data ->
                        resetPasswordScreenUiState =
                            ResetPasswordScreenUiState(
                                viewModelResult = ViewModelResult.SUCCESS,
                                message = repositoryResult.message,
                                data = repositoryResult.data
                            )
                    }
                }
                is RepositoryResult.Error -> {
                    resetPasswordScreenUiState = ResetPasswordScreenUiState(
                        viewModelResult = ViewModelResult.ERROR,
                        message = repositoryResult.message
                    )
                }
                is RepositoryResult.Local -> {}
            }
        }
    }

}