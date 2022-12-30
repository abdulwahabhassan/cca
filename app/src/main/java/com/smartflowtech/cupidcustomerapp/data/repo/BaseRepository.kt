package com.smartflowtech.cupidcustomerapp.data.repo

import com.smartflowtech.cupidcustomerapp.data.api.CupidApiErrorResponse
import com.smartflowtech.cupidcustomerapp.model.result.NetworkResult
import com.smartflowtech.cupidcustomerapp.network.NetworkConnectivityManager
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

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
                //Counter to retry three times in case of IO Exception
                var count = 0
                invokeApiRequest(apiRequest) {
                    count += 1
                    count
                }
            }
        }
    }

    private suspend fun <T> invokeApiRequest(
        apiRequest: suspend () -> T,
        increaseRetryCounter: () -> Int
    ): NetworkResult<T> {
        return try {
            NetworkResult.Success(apiRequest.invoke())
        }
        catch (e: HttpException) {
            Timber.d("Http Exception Error Http Status Code - ${e.code()} ")
            val response = handleHttpException(e)
            NetworkResult.Error(
                message = response?.message?.capitalizeFirstLetter()
                    ?: response?.error?.capitalizeFirstLetter()
            )
        }
        catch (e: IOException) {
            Timber.d("IO Exception Error ${e.localizedMessage}")
            val count = increaseRetryCounter()
            Timber.d("IO Exception Counter $count")
            if (count < 3)  {
                invokeApiRequest(
                    apiRequest = apiRequest,
                    increaseRetryCounter = increaseRetryCounter
                )
            } else {
                NetworkResult.Error(e.localizedMessage)
            }
        } catch (e: Exception) {
            Timber.d("Exception Error ${e.localizedMessage}")
            NetworkResult.Error(message = "Oops! Something went wrong")
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
            CupidApiErrorResponse(message = "Oops! An error occurred (${e.code()})")
        }
    }

}

