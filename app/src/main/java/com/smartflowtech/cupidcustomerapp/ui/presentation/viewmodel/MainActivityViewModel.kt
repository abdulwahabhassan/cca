package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.NotificationsRepository
import com.smartflowtech.cupidcustomerapp.model.request.UpdateDeviceTokenRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository,
    private val notificationsRepository: NotificationsRepository
) : BaseViewModel(dataStorePrefsRepository = dataStorePrefsRepository) {

    fun updateDeviceToken(fcmToken: String) {
        viewModelScope.launch {
            val result = notificationsRepository.updateDeviceToken(
                token = dataStorePrefsRepository.getAppConfigPreferences().token,
                updateDeviceTokenRequestBody = UpdateDeviceTokenRequestBody(
                    dataStorePrefsRepository.getAppConfigPreferences().userId,
                    deviceToken = fcmToken
                )
            )
            when (result) {
                is RepositoryResult.Error -> {
                    Timber.d("$result")
                }

                is RepositoryResult.Success -> {
                    Timber.d("$result")
                }
            }
        }
    }
}