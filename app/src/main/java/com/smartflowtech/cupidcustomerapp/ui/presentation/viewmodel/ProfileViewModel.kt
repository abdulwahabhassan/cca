package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.ProfileRepository
import com.smartflowtech.cupidcustomerapp.model.domain.CompanyUser
import com.smartflowtech.cupidcustomerapp.model.request.UpdateProfileRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.profile.UpdateProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository,
    private val profileRepository: ProfileRepository
) : BaseViewModel(dataStorePrefsRepository) {

    suspend fun updateProfile(firstName: String, lastName: String, email: String): UpdateProfileState {
        return when (val repositoryResult = profileRepository.updateProfile(
            token = appConfigPreferences.token,
            userId = appConfigPreferences.userId,
            updateProfileRequestBody = UpdateProfileRequestBody(
                companyUser = CompanyUser(
                    id = appConfigPreferences.userId,
                    email = email,
                    userName = appConfigPreferences.userName,
                    companyId = appConfigPreferences.companyId,
                    fullName = "$firstName $lastName",
                    phoneNumber = appConfigPreferences.phoneNumber
                )
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
                    UpdateProfileState(
                        viewModelResult = ViewModelResult.SUCCESS,
                        data = data,
                        message = repositoryResult.message
                    )
                } ?: UpdateProfileState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = "Response data not found!"
                )
            }
            is RepositoryResult.Error -> {
                UpdateProfileState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = repositoryResult.message
                )
            }
        }
    }

}