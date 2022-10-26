package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.SettingsRepository
import com.smartflowtech.cupidcustomerapp.model.request.ChangePasswordRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.ChangePasswordScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository,
    private val settingsRepository: SettingsRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var changePasswordScreenUiState by mutableStateOf(ChangePasswordScreenUiState(ViewModelResult.INITIAL))
        private set

    fun changePassword(currentPassword: String, newPassword: String) {
        viewModelScope.launch {

            changePasswordScreenUiState =
                ChangePasswordScreenUiState(viewModelResult = ViewModelResult.LOADING)

            when (val repositoryResult = settingsRepository.changePassword(
                token = appConfigPreferences.token,
                userId = appConfigPreferences.userId,
                changePasswordRequestBody = ChangePasswordRequestBody(
                    appConfigPreferences.userId,
                    email = appConfigPreferences.userEmail,
                    userName = appConfigPreferences.userName,
                    companyId = appConfigPreferences.companyId,
                    currentPassword = currentPassword,
                    password = newPassword,
                    repeatPassword = newPassword
                )
            )) {
                is RepositoryResult.Success -> {
                    repositoryResult.data?.let { data ->
                        changePasswordScreenUiState =
                            ChangePasswordScreenUiState(
                                viewModelResult = ViewModelResult.SUCCESS,
                                data = data,
                                message = repositoryResult.message
                            )
                    }
                }
                is RepositoryResult.Error -> {
                    changePasswordScreenUiState = ChangePasswordScreenUiState(
                        viewModelResult = ViewModelResult.ERROR,
                        message = repositoryResult.message
                    )
                }
                is RepositoryResult.Local -> {}
            }
        }
    }

}