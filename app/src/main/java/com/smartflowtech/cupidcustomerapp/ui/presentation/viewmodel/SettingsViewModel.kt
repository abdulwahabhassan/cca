package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository
) : BaseViewModel(dataStorePrefsRepository) {


    override val viewModelName: String
        get() = "Settings View Model"

    fun logOut() {
        viewModelScope.launch {
            dataStorePrefsRepository.updateLoggedIn(loggedIn = false)
        }
    }

    fun updateEmailNotifications(bool: Boolean) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateEmailNotifications(bool)
        }
    }

    fun updatePushNotifications(bool: Boolean) {
        viewModelScope.launch {
            dataStorePrefsRepository.updatePushNotifications(bool)
        }
    }

    fun updatePaymentMethod(name: String) {
        viewModelScope.launch {
            dataStorePrefsRepository.updatePaymentMethod(name)
        }
    }
}