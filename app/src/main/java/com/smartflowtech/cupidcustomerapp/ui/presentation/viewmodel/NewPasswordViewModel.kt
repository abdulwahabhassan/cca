package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.ProfileRepository
import com.smartflowtech.cupidcustomerapp.model.request.UpdateProfileRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.NewPasswordScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPasswordViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var newPasswordScreenUiState by mutableStateOf(NewPasswordScreenUiState(ViewModelResult.INITIAL))
        private set

    fun updateProfile(newPassword: String) {
        viewModelScope.launch {

            newPasswordScreenUiState = NewPasswordScreenUiState(viewModelResult = ViewModelResult.LOADING)

            when (val repositoryResult = profileRepository.updateProfile(
                token = appConfigPreferences.token,
                userId = appConfigPreferences.userId,
                updateProfileRequestBody = UpdateProfileRequestBody(
                    appConfigPreferences.userId,
                    fullName = appConfigPreferences.userName,
                    email = appConfigPreferences.userEmail,
                    userName = appConfigPreferences.userName,
                    isCompanySetUp = null,
                    phoneNumber = appConfigPreferences.phoneNumber,
                    isVerified = 1,
                    companyId = appConfigPreferences.companyId,
                    status = "Active",
                    createdAt = null,
                    updatedAt = null,
                    deletedAt = null,
                    firstName = null,
                    lastName = null,
                    token = appConfigPreferences.token,
                    currentPassword = newPassword,
                    password = newPassword,
                    repeatPassword = newPassword,
                )
            )) {
                is RepositoryResult.Success -> {
                    repositoryResult.data?.let { data ->
                        newPasswordScreenUiState =
                            NewPasswordScreenUiState(
                                viewModelResult = ViewModelResult.SUCCESS,
                                data = data,
                                message = repositoryResult.message
                            )
                    }
                }
                is RepositoryResult.Error -> {
                    newPasswordScreenUiState = NewPasswordScreenUiState(
                        viewModelResult = ViewModelResult.ERROR,
                        message = repositoryResult.message
                    )
                }
                is RepositoryResult.Local -> {}
            }
        }
    }

}