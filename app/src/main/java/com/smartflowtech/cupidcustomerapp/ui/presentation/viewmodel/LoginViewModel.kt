package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.LoginRepository
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.login.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataStorePrefsRepository: DataStorePrefsRepository
) : BaseViewModel(dataStorePrefsRepository) {

    suspend fun login(email: String, password: String): LoginState {
        return when (
            val repositoryResult = loginRepository.login(
                LoginRequestBody(
                    email = email,
                    password = password
                )
            )) {
            is RepositoryResult.Success -> {
                repositoryResult.data?.let { data ->
                    dataStorePrefsRepository.persistUser(
                        userName = data.username ?: "",
                        userEmail = data.email ?: "",
                        phoneNumber = data.phoneNumber ?: "",
                        companyId = data.companyId ?: "",
                        userId = data.id?.toString() ?: "",
                        fullName = data.fullname ?: ""
                    )
                    dataStorePrefsRepository.persistToken(token = "Bearer ${data.token}")
                    dataStorePrefsRepository.updateLoggedIn(loggedIn = true)
                    dataStorePrefsRepository.updateVendorData(
                        vendorId = data.vendorId ?: -1,
                        bankAcctName = data.vendorBankAccountData?.bankName ?: "",
                        bankAcctNum = data.vendorBankAccountData?.accountNumber ?: "",
                        bankName = data.vendorBankAccountData?.bankName ?: ""
                    )

                    LoginState(
                        viewModelResult = ViewModelResult.SUCCESS,
                        message = repositoryResult.message
                    )
                } ?: LoginState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = "Response data not found!"
                )
            }
            is RepositoryResult.Error -> {
                LoginState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = repositoryResult.message
                )
            }

        }
    }

}