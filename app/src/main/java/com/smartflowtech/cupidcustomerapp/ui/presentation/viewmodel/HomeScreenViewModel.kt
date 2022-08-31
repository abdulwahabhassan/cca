package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.model.Product
import com.smartflowtech.cupidcustomerapp.model.Status
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var transactions by mutableStateOf(emptyList<Transaction>())

    init {
        getTransactions()
    }

    private fun getTransactions() {
        viewModelScope.launch {
            combine(
                flowOf(Transaction.transactions),
                dataStorePrefsRepository.appConfigPreferencesAsFlow
            ) { list, prefs ->
                list.filter { transaction ->
                    LocalDate.parse(transaction.date) >= LocalDate.now().minusDays(prefs.daysFilter)
                }.filter { transaction ->
                    when (transaction.status) {
                        Status.COMPLETED.name.capitalizeFirstLetter() ->
                            if (prefs.completedStatusFilter)
                                transaction.status == Status.COMPLETED.name.capitalizeFirstLetter()
                            else false
                        Status.FAILED.name.capitalizeFirstLetter() ->
                            if (prefs.failedStatusFilter)
                                transaction.status == Status.FAILED.name.capitalizeFirstLetter()
                            else false
                        Status.PENDING.name.capitalizeFirstLetter() ->
                            if (prefs.pendingStatusFilter)
                                transaction.status == Status.PENDING.name.capitalizeFirstLetter()
                            else false
                        else -> false
                    }
                }.filter { transaction ->
                    when (transaction.product) {
                        Product.DPK.name ->
                            if (prefs.dpkProductFilter)
                                transaction.product == Product.DPK.name
                            else false
                        Product.PMS.name ->
                            if (prefs.pmsProductFilter)
                                transaction.product == Product.PMS.name
                            else false
                        Product.AGO.name ->
                            if (prefs.agoProductFilter)
                                transaction.product == Product.AGO.name
                            else false
                        else -> false
                    }
                }
            }.collectLatest {
                transactions = it
            }
        }
    }


    fun updateWalletBalanceVisibility(visibility: Boolean) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateWalletBalanceVisibility(visibility)
        }
    }

    fun updateDateFilter(date: Long) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateDaysFilter(date)
        }
    }

    fun updateCompletedStatusFilter(bool: Boolean) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateCompletedStatusFilter(bool)
        }
    }

    fun updateFailedStatusFilter(bool: Boolean) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateFailedStatusFilter(bool)
        }
    }

    fun updatePendingStatusFilter(bool: Boolean) {
        viewModelScope.launch {
            dataStorePrefsRepository.updatePendingStatusFilter(bool)
        }
    }

    fun updateDpkProduct(bool: Boolean) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateDpkProductFilter(bool)
        }
    }

    fun updatePmsProduct(bool: Boolean) {
        viewModelScope.launch {
            dataStorePrefsRepository.updatePmsProductFilter(bool)
        }
    }

    fun updateAgoProduct(bool: Boolean) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateAgoProductFilter(bool)
        }
    }

}