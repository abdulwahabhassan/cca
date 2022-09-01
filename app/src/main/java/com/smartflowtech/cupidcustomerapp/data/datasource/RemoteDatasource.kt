package com.smartflowtech.cupidcustomerapp.data.datasource

import com.smartflowtech.cupidcustomerapp.data.api.CupidApiResponse
import com.smartflowtech.cupidcustomerapp.data.api.CupidApiService
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.response.LoginResponseData
import com.smartflowtech.cupidcustomerapp.model.response.TransactionsResponseData
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    private val apiService: CupidApiService
) {
    suspend fun login(
        loginRequestBody: LoginRequestBody
    ): CupidApiResponse<LoginResponseData> {
        return apiService.login(
            loginRequestBody = loginRequestBody
        )
    }

    suspend fun getTransactions(
        token: String,
        companyId: String
    ): CupidApiResponse<List<TransactionsResponseData>> {
        return apiService.getTransactions(
            token = token,
            companyId = companyId
        )
    }
}