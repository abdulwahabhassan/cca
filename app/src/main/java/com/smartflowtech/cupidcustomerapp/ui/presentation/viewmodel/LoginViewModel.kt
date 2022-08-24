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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val dataStorePrefsRepository: DataStorePrefsRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var loginScreenUiState by mutableStateOf(LoginScreenUiState(ViewModelResult.INITIAL))
        private set

    fun login(loginRequestBody: LoginRequestBody) {
        viewModelScope.launch {

            loginScreenUiState = LoginScreenUiState(viewModelResult = ViewModelResult.LOADING)

            when (val repositoryResult = loginRepository.login(loginRequestBody)) {
                is RepositoryResult.Success -> {
                    repositoryResult.data?.let { data ->
                        dataStorePrefsRepository.updateLoggedIn(
                            true,
                            data.fullname ?: "",
                            data.email ?: ""
                        )
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