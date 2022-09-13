package com.smartflowtech.cupidcustomerapp.data.repo

import com.smartflowtech.cupidcustomerapp.data.datasource.RemoteDatasource
import com.smartflowtech.cupidcustomerapp.model.result.NetworkResult
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.network.NetworkConnectivityManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val remoteDatasource: RemoteDatasource,
    private val networkConnectivityManager: NetworkConnectivityManager,
    private val dispatcher: CoroutineDispatcher
) : BaseRepository() {

    suspend fun login(loginRequestBody: LoginRequestBody) = withContext(dispatcher) {
        when (
            val networkResult = coroutineHandler(dispatcher, networkConnectivityManager) {
                remoteDatasource.login(loginRequestBody = loginRequestBody)
            }
        ) {
            is NetworkResult.Success -> {
                Timber.d(
                    "Success -> ${
                        """id: ${networkResult.payload.data?.id} 
                           companyId: ${networkResult.payload.data?.companyId}
                           token: ${networkResult.payload.data?.token}
                           email: ${networkResult.payload.data?.email}
                           fullname: ${networkResult.payload.data?.fullname}
                        """
                    }"
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