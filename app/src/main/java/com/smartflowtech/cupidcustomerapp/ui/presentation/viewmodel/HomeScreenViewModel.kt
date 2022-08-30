package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository
) : BaseViewModel(dataStorePrefsRepository) {
    fun updateWalletBalanceVisibility(visibility: Boolean) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateWalletBalanceVisibility(visibility)
        }
    }

    fun updateDateFilter(date: String) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateDateFilter(date)
        }
    }

    fun updateStatusFilter(status: String) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateStatusFilter(status)
        }
    }

    fun updateProduct(product: String) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateProductFilter(product)
        }
    }
}