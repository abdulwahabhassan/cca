package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.model.domain.AppConfigPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository
) : ViewModel() {

    var appConfigPreferences by mutableStateOf(AppConfigPreferences())
        private set
    open val viewModelName: String = "Base View Model"

    init {
        Timber.d("$viewModelName initialized!")
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