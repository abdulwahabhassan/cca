package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.TransactionRepository
import com.smartflowtech.cupidcustomerapp.data.repo.WalletRepository
import com.smartflowtech.cupidcustomerapp.model.*
import com.smartflowtech.cupidcustomerapp.model.response.TransactionsResponseData
import com.smartflowtech.cupidcustomerapp.model.response.WalletResponseData
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.HomeScreenUiState
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

    var homeScreenUiState by mutableStateOf(
        HomeScreenUiState(
            viewModelResult = ViewModelResult.INITIAL,
            transactions = emptyList(),
            wallets = emptyList()
        )
    )

    init {
        getTransactionsAndWallets()
    }

    fun getTransactionsAndWallets() {

        homeScreenUiState = homeScreenUiState.copy(viewModelResult = ViewModelResult.LOADING)

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
                        HomeScreenUiState(
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
                        HomeScreenUiState(
                            viewModelResult = ViewModelResult.ERROR,
                            transactions = emptyList(),
                            message = transactionsResult.message,
                            wallets = wallets ?: emptyList()
                        )
                    }
                    is RepositoryResult.Local -> {
                        HomeScreenUiState(
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
                homeScreenUiState = it
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
//            Timber.d("$transaction")
            LocalDate.parse(transaction.date) >= LocalDate.now()
                .minusDays(when(prefs.daysFilter) {
                    Days.TODAY.name -> 0L
                    Days.ONE_WEEK.name -> 7L
                    Days.TWO_WEEKS.name -> 14L
                    Days.ONE_MONTH.name -> 30L
                    Days.SIX_MONTHS.name -> 182L
                    Days.ONE_YEAR.name -> 365L
                    Days.TWO_YEARS.name -> 720L
                    else -> {0L}
                })
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

    fun updateTransactionFilters(daysFilter: String, mapOfFilters: Map<String, Boolean>) {
        Timber.d("$daysFilter $mapOfFilters")
        viewModelScope.launch {
            dataStorePrefsRepository.updateTransactionFilters(daysFilter, mapOfFilters)
        }
    }

}