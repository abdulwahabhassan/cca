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

    fun logOut() {
        viewModelScope.launch {
            dataStorePrefsRepository.updateLoggedIn(loggedIn = false)
        }
    }
}