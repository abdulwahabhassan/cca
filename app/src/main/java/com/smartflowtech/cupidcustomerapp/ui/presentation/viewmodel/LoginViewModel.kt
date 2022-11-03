package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.LoginRepository
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.login.LoginScreenUiState
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeEachWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataStorePrefsRepository: DataStorePrefsRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var loginScreenUiState by mutableStateOf(LoginScreenUiState(ViewModelResult.INITIAL))
        private set

    fun login(loginRequestBody: LoginRequestBody) {
        loginScreenUiState = LoginScreenUiState(viewModelResult = ViewModelResult.LOADING)
        viewModelScope.launch {
            when (val repositoryResult = loginRepository.login(loginRequestBody)) {
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
                        loginScreenUiState =
                            LoginScreenUiState(
                                viewModelResult = ViewModelResult.SUCCESS,
                                message = repositoryResult.message
                            )
                    }
                }
                is RepositoryResult.Error -> {
                    loginScreenUiState = LoginScreenUiState(
                        viewModelResult = ViewModelResult.ERROR,
                        message = repositoryResult.message
                    )
                }
                is RepositoryResult.Local -> {}
            }
        }
    }

}