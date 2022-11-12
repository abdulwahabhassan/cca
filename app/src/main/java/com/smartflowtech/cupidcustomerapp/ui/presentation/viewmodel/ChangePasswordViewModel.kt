package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.LoginRepository
import com.smartflowtech.cupidcustomerapp.data.repo.SettingsRepository
import com.smartflowtech.cupidcustomerapp.model.domain.CompanyUser
import com.smartflowtech.cupidcustomerapp.model.request.ChangePasswordRequestBody
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.ChangePasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository,
    private val settingsRepository: SettingsRepository,
    private val loginRepository: LoginRepository
) : BaseViewModel(dataStorePrefsRepository) {

    suspend fun changePassword(currentPassword: String, newPassword: String): ChangePasswordState {
        //Attempt to log the user in first, using email persisted in reset password view model,
        //if successful, persist login data including the token from the response
        //which will be used to change password
        return when (val repositoryResult = loginRepository.login(
            loginRequestBody = LoginRequestBody(
                email = appConfigPreferences.userEmail,
                password = currentPassword
            )
        )) {
            is RepositoryResult.Success -> {
                repositoryResult.data?.let { data ->
                    Timber.d("View model Login Data $data")
                    dataStorePrefsRepository.persistToken(token = "Bearer ${data.token}")
                    dataStorePrefsRepository.updateVendorData(
                        vendorId = data.vendorId ?: -1,
                        bankAcctName = data.vendorBankAccountData?.bankName ?: "",
                        bankAcctNum = data.vendorBankAccountData?.accountNumber ?: "",
                        bankName = data.vendorBankAccountData?.bankName ?: ""
                    )
                    doChangePassword(currentPassword, newPassword)
                } ?: ChangePasswordState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = "Response data not found!"
                )
            }
            is RepositoryResult.Error -> {
                ChangePasswordState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = repositoryResult.message
                )
            }
        }
    }

    private suspend fun doChangePassword(
        currentPassword: String,
        newPassword: String
    ): ChangePasswordState {
        return when (val repositoryResult = settingsRepository.changePassword(
            token = appConfigPreferences.token,
            userId = appConfigPreferences.userId,
            changePasswordRequestBody = ChangePasswordRequestBody(
                companyUser = CompanyUser(
                    id = appConfigPreferences.userId,
                    email = appConfigPreferences.userEmail,
                    userName = appConfigPreferences.userName,
                    companyId = appConfigPreferences.companyId,
                    currentPassword = currentPassword,
                    password = newPassword,
                    repeatPassword = newPassword
                )
            )
        )) {
            is RepositoryResult.Success -> {
                //Automatically log user in to the app if password change was successful
                repositoryResult.data?.let { data ->
                    dataStorePrefsRepository.updateLoggedIn(true)
                    ChangePasswordState(
                        viewModelResult = ViewModelResult.SUCCESS,
                        data = data,
                        message = repositoryResult.message
                    )
                } ?: ChangePasswordState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = "Response data not found!"
                )
            }
            is RepositoryResult.Error -> {
                ChangePasswordState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = repositoryResult.message
                )
            }
        }
    }

}