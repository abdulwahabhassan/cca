package com.smartflowtech.cupidcustomerapp.data.repo

import com.smartflowtech.cupidcustomerapp.data.api.CupidApiErrorResponse
import com.smartflowtech.cupidcustomerapp.data.api.CupidApiResponse
import com.smartflowtech.cupidcustomerapp.model.result.NetworkResult
import com.smartflowtech.cupidcustomerapp.network.NetworkConnectivityManager
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber

@Suppress("UNCHECKED_CAST")
open class BaseRepository {

    suspend fun <T> coroutineHandler(
        dispatcher: CoroutineDispatcher,
        networkConnectivityManager: NetworkConnectivityManager,
        apiRequest: suspend () -> T
    ): NetworkResult<T> {
        return withContext(dispatcher) {
            if (!networkConnectivityManager.isNetworkAvailable()) {
                NetworkResult.Error("Check your internet connection!")
            } else {
                try {
                    NetworkResult.Success(apiRequest.invoke())
                } catch (e: HttpException) {
                    Timber.d("Http Error Http Status Code - ${e.code()} ")
                    val response = handleHttpException(e)
                    NetworkResult.Error(message = response?.message?.capitalizeFirstLetter())
                } catch (e: Exception) {
                    Timber.d("Exception Error ${e.message}")
                    NetworkResult.Error(message = e.localizedMessage)
                }
            }
        }
    }

    private fun handleHttpException(e: HttpException): CupidApiErrorResponse? {
        return try {
            e.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder()
                    .addLast(KotlinJsonAdapterFactory())
                    .build()
                    .adapter(CupidApiErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (t: Throwable) {
            Timber.d("Error while handling httpException $t")
            CupidApiErrorResponse(message = "Oops! Service is unavailable (${e.code()})")
        }
    }

}

