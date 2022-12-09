package com.smartflowtech.cupidcustomerapp.data.repo

import com.smartflowtech.cupidcustomerapp.data.datasource.RemoteDatasource
import com.smartflowtech.cupidcustomerapp.database.TransactionsDao
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.request.TransactionReportRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.NetworkResult
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.network.NetworkConnectivityManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val networkConnectivityManager: NetworkConnectivityManager,
    private val dispatcher: CoroutineDispatcher,
    private val transactionsDao: TransactionsDao
) : BaseRepository() {

    suspend fun getTransactions(token: String, companyId: String) = withContext(dispatcher) {
        when (
            val networkResult = coroutineHandler(dispatcher, networkConnectivityManager) {

                remoteDatasource.getTransactions(
                    token = token,
                    companyId = companyId
                )
            }
        ) {
            is NetworkResult.Success -> {
                Timber.d("Success -> ${networkResult.payload}")
                if (networkResult.payload.status) {
                    //cache remote data locally
                    transactionsDao.insertTransactions(
                        networkResult.payload.data?.map { it.mapToTransactionEntity() }
                            ?: emptyList()
                    )
                    //serve data from local storage
                    RepositoryResult.Success(
                        data = transactionsDao.getTransactions().map { it.mapToTransaction() },
                        message = networkResult.payload.message
                    )
                } else {
                    RepositoryResult.Error(message = networkResult.payload.message)
                }
            }
            is NetworkResult.Error -> {
                //fetch from local storage if available
                Timber.d("Error -> ${networkResult.message}")
                val localTransactions = transactionsDao.getTransactions().map {
                    it.mapToTransaction()
                }
                if (localTransactions.isNotEmpty()) {
                    RepositoryResult.Success(data = localTransactions, message = networkResult.message)
                } else {
                    RepositoryResult.Error(message = networkResult.message)
                }
            }
        }
    }

    suspend fun getTransactionReport(
        token: String,
        transactionReportRequestBody: TransactionReportRequestBody
    ) = withContext(dispatcher) {
        when (
            val networkResult = coroutineHandler(dispatcher, networkConnectivityManager) {

                remoteDatasource.getTransactionReport(
                    token = token,
                    transactionReportRequestBody = transactionReportRequestBody
                )
            }
        ) {
            is NetworkResult.Success -> {
                Timber.d("Success -> ${networkResult.payload}")
                if (networkResult.payload.status) {
                    RepositoryResult.Success(
                        data = networkResult.payload.data,
                        message = networkResult.payload.message
                    )
                } else {
                    RepositoryResult.Error(message = networkResult.payload.message)
                }
            }
            is NetworkResult.Error -> {
                Timber.d("Error -> ${networkResult.message}")
                RepositoryResult.Error(message = networkResult.message)
            }
        }
    }
}