package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.PasswordRepository
import com.smartflowtech.cupidcustomerapp.model.request.VerifyEmailRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.ResetPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val passwordRepository: PasswordRepository,
    private val dataStorePrefsRepository: DataStorePrefsRepository
) : BaseViewModel(dataStorePrefsRepository) {

    suspend fun forgotPasswordVerifyEmail(email: String): ResetPasswordState {
        return when (val repositoryResult = passwordRepository.forgotPasswordVerifyEmail(
            token = appConfigPreferences.token,
            VerifyEmailRequestBody(email = email)
        )) {
            is RepositoryResult.Success -> {
                repositoryResult.data?.let { data ->
                    //persist user
                    dataStorePrefsRepository.persistUser(
                        userName = data.username ?: "",
                        userEmail = data.email ?: "",
                        phoneNumber = data.phoneNumber ?: "",
                        companyId = data.companyID ?: "",
                        userId = data.id?.toString() ?: "",
                        fullName = data.fullname ?: "",
                    )
                    ResetPasswordState(
                        viewModelResult = ViewModelResult.SUCCESS,
                        message = repositoryResult.message,
                        data = repositoryResult.data
                    )
                } ?: ResetPasswordState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = "Response data not found"
                )
            }
            is RepositoryResult.Error -> {
                ResetPasswordState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = repositoryResult.message
                )
            }
        }
    }

}