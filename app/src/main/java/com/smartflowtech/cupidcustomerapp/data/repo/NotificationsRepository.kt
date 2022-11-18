package com.smartflowtech.cupidcustomerapp.data.repo

import com.smartflowtech.cupidcustomerapp.data.datasource.RemoteDatasource
import com.smartflowtech.cupidcustomerapp.model.request.AddDeviceTokenRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.NetworkResult
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.network.NetworkConnectivityManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class NotificationsRepository @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val networkConnectivityManager: NetworkConnectivityManager,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

//    suspend fun getNotifications() = withContext(dispatcher) {
//
//        RepositoryResult.Success(
//            data = Util.getListOfNotifications()
//        )
//    }

    suspend fun getNotifications(
        token: String,
        userId: String
    ) = withContext(dispatcher) {
        when (
            val networkResult = coroutineHandler(dispatcher, networkConnectivityManager) {
                remoteDatasource.getNotifications(
                    token = token,
                    userId = userId
                )
            }
        ) {
            is NetworkResult.Success -> {
                Timber.d("Success -> ${networkResult.payload.data}")
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

    suspend fun addDeviceToken(
        token: String,
        addDeviceTokenRequestBody: AddDeviceTokenRequestBody
    ) = withContext(dispatcher) {
        when (
            val networkResult = coroutineHandler(dispatcher, networkConnectivityManager) {
                remoteDatasource.addDeviceToken(
                    token = token,
                    addDeviceTokenRequestBody = addDeviceTokenRequestBody
                )
            }
        ) {
            is NetworkResult.Success -> {
                Timber.d("Success -> ${networkResult.payload.data}")
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