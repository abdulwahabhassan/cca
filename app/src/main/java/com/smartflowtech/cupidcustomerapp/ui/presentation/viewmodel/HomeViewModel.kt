package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.TransactionRepository
import com.smartflowtech.cupidcustomerapp.data.repo.WalletRepository
import com.smartflowtech.cupidcustomerapp.model.domain.*
import com.smartflowtech.cupidcustomerapp.model.response.TransactionsData
import com.smartflowtech.cupidcustomerapp.model.response.WalletData
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.HomeScreenUiState
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
class HomeViewModel @Inject constructor(
    private val dataStorePrefsRepository: DataStorePrefsRepository,
    private val transactionRepository: TransactionRepository,
    private val walletRepository: WalletRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var homeScreenUiState by mutableStateOf(
        HomeScreenUiState(
            viewModelResult = ViewModelResult.LOADING
        )
    )
        private set

    init {
        getTransactionsAndWallets()
    }

    fun getTransactionsAndWallets() {
        homeScreenUiState = HomeScreenUiState(viewModelResult = ViewModelResult.LOADING)
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
                            wallets = wallets ?: emptyList(),
                        )
                    }
                    is RepositoryResult.Error -> {
                        HomeScreenUiState(
                            viewModelResult = ViewModelResult.ERROR,
                            transactions = emptyList(),
                            message = transactionsResult.message,
                            wallets = wallets ?: emptyList(),
                        )
                    }
                }
            }.collectLatest {
                homeScreenUiState = it
            }
        }
    }

    private fun mapWalletsResponseData(data: List<WalletData>?): List<Wallet>? {
        return data?.map { walletResponseData -> walletResponseData.mapToWallet() }
    }

    private fun mapTransactionsResponseData(
        result: List<TransactionsData>?,
        prefs: AppConfigPreferences
    ): List<Transaction>? {
        return result?.map { it.mapToTransaction() }?.filter { transaction ->
            Timber.d("$transaction")
            LocalDate.parse(transaction.date) >= LocalDate.now()
                .minusDays(
                    when (prefs.periodFilter) {
                        Period.TODAY.name -> 0L
                        Period.ONE_WEEK.name -> 7L
                        Period.TWO_WEEKS.name -> 14L
                        Period.ONE_MONTH.name -> 30L
                        Period.SIX_MONTHS.name -> 182L
                        Period.ONE_YEAR.name -> 365L
                        Period.TWO_YEARS.name -> 720L
                        else -> {
                            0L
                        }
                    }
                )
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

    fun updateTransactionFilters(daysFilter: String, mapOfFilters: Map<String, Boolean>) {
        Timber.d("$daysFilter $mapOfFilters")
        viewModelScope.launch {
            dataStorePrefsRepository.updateTransactionFilters(daysFilter, mapOfFilters)
        }
    }

    fun persistProfilePicture(uri: String) {
        viewModelScope.launch {
            dataStorePrefsRepository.persistProfilePicture(uri)
        }
    }

    fun updateStationFilter(filter: String) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateStationFilter(filter)
        }
    }

    fun updateNotificationsFilter(filter: String) {
        viewModelScope.launch {
            dataStorePrefsRepository.updateNotificationsFilter(filter)
        }
    }

}