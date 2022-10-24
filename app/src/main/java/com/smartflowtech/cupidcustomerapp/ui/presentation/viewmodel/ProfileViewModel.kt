package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.LoginRepository
import com.smartflowtech.cupidcustomerapp.data.repo.ProfileRepository
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.request.UpdateProfileRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.login.LoginScreenUiState
import com.smartflowtech.cupidcustomerapp.ui.presentation.profile.Profile
import com.smartflowtech.cupidcustomerapp.ui.presentation.profile.ProfileScreenUiState
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeEachWord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var profileScreenUiState by mutableStateOf(ProfileScreenUiState(ViewModelResult.INITIAL))
        private set

    fun updateProfile(updateProfileRequestBody: UpdateProfileRequestBody) {
        viewModelScope.launch {

            profileScreenUiState = ProfileScreenUiState(viewModelResult = ViewModelResult.LOADING)

            when (val repositoryResult = profileRepository.updateProfile(
                token = appConfigPreferences.token,
                userId = appConfigPreferences.userId,
                updateProfileRequestBody = updateProfileRequestBody
            )) {
                is RepositoryResult.Success -> {
                    repositoryResult.data?.let { data ->
                        profileScreenUiState =
                            ProfileScreenUiState(
                                viewModelResult = ViewModelResult.SUCCESS,
                                data = data,
                                message = repositoryResult.message
                            )
                    }
                }
                is RepositoryResult.Error -> {
                    profileScreenUiState = ProfileScreenUiState(
                        viewModelResult = ViewModelResult.ERROR,
                        message = repositoryResult.message
                    )
                }
                is RepositoryResult.Local -> {}
            }
        }
    }

}