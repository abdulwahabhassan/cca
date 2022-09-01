package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.TransactionRepository
import com.smartflowtech.cupidcustomerapp.data.repo.WalletRepository
import com.smartflowtech.cupidcustomerapp.model.Product
import com.smartflowtech.cupidcustomerapp.model.Status
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.model.Wallet
import com.smartflowtech.cupidcustomerapp.model.response.TransactionsResponseData
import com.smartflowtech.cupidcustomerapp.model.response.WalletResponseData
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.TransactionHistoryUiState
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository,
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var transactions by mutableStateOf(
        TransactionHistoryUiState(
            viewModelResult = ViewModelResult.LOADING,
            transactions = emptyList(),
            wallets = emptyList()
        )
    )

    init {
        getTransactionsAndWallets()
    }


    private fun getTransactionsAndWallets() {
        viewModelScope.launch {
            combine(
                flowOf(
                    transactionRepository.getTransactions(
                        token = appConfigPreferences.token,
                        companyId = appConfigPreferences.companyId
                    )
                ),
                dataStorePrefsRepository.appConfigPreferencesAsFlow,
                flowOf(
                    walletRepository.getWallets(
                        appConfigPreferences.token,
                        appConfigPreferences.companyId
                    )
                )
            ) { transactionsResult, prefs, walletsResult ->

                val wallets = when (walletsResult) {
                    is RepositoryResult.Success -> {
                        mapWalletsResponseData(walletsResult.data)
                    }
                    is RepositoryResult.Error -> {
                        emptyList()
                    }
                    is RepositoryResult.Local -> {
                        mapWalletsResponseData(walletsResult.data)
                    }
                }

                when (transactionsResult) {
                    is RepositoryResult.Success -> {
                        TransactionHistoryUiState(
                            viewModelResult = ViewModelResult.SUCCESS,
                            transactions = mapTransactionsResponseData(
                                transactionsResult.data,
                                prefs
                            )
                                ?: emptyList(),
                            wallets = wallets ?: emptyList()
                        )
                    }
                    is RepositoryResult.Error -> {
                        TransactionHistoryUiState(
                            viewModelResult = ViewModelResult.ERROR,
                            transactions = emptyList(),
                            message = transactionsResult.message,
                            wallets = wallets ?: emptyList()
                        )
                    }
                    is RepositoryResult.Local -> {
                        TransactionHistoryUiState(
                            viewModelResult = ViewModelResult.SUCCESS,
                            transactions = mapTransactionsResponseData(
                                transactionsResult.data,
                                prefs
                            )
                                ?: emptyList(),
                            wallets = wallets ?: emptyList()
                        )
                    }
                }
            }.collectLatest {
                transactions = it
            }
        }
    }

    private fun mapWalletsResponseData(data: List<WalletResponseData>?): List<Wallet>? {
        return data?.map { walletResponseData -> walletResponseData.mapToWallet() }
    }

    private fun mapTransactionsResponseData(
        result: List<TransactionsResponseData>?,
        prefs: DataStorePrefsRepository.AppConfigPreferences
    ): List<Transaction>? {
        return result?.map { it.mapToTransaction() }?.filter { transaction ->
            Timber.d("$transaction")
            LocalDate.parse(transaction.date) >= LocalDate.now()
                .minusDays(prefs.daysFilter)
        }?.filter { transaction ->
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
        }?.filter { transaction ->
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

    fun logOut() {
        viewModelScope.launch {
            dataStorePrefsRepository.updateLoggedIn(
                false,
                appConfigPreferences.userName,
                appConfigPreferences.userEmail,
                appConfigPreferences.token,
                appConfigPreferences.phoneNumber,
                appConfigPreferences.companyId
            )
        }
    }

}