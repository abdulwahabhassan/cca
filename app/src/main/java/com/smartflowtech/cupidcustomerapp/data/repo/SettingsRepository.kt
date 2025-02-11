package com.smartflowtech.cupidcustomerapp.data.repo

import com.smartflowtech.cupidcustomerapp.data.datasource.RemoteDatasource
import com.smartflowtech.cupidcustomerapp.model.request.ChangePasswordRequestBody
import com.smartflowtech.cupidcustomerapp.model.request.UpdateProfileRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.NetworkResult
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.network.NetworkConnectivityManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val networkConnectivityManager: NetworkConnectivityManager,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    suspend fun changePassword(
        token: String,
        userId: String,
        changePasswordRequestBody: ChangePasswordRequestBody
    ) = withContext(dispatcher) {
        when (
            val networkResult = coroutineHandler(dispatcher, networkConnectivityManager) {
                remoteDatasource.changePassword(token, userId, changePasswordRequestBody)
            }
        ) {
            is NetworkResult.Success -> {
                Timber.d(
                    "Success -> ${networkResult.payload.data}"
                )
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