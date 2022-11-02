package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.model.domain.AppConfigPreferences
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


open class BaseViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository
) : ViewModel() {

    var appConfigPreferences by mutableStateOf(AppConfigPreferences())
        private set

    init {
        collectAppConfigPreferences()
    }

    private fun collectAppConfigPreferences() {
        viewModelScope.launch {
            dataStorePrefsRepository.appConfigPreferencesAsFlow.collectLatest { prefs ->
                appConfigPreferences = prefs
            }
        }
    }

}