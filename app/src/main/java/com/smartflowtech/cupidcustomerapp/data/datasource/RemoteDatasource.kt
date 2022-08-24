package com.smartflowtech.cupidcustomerapp.data.datasource

import com.smartflowtech.cupidcustomerapp.data.api.CupidApiResponse
import com.smartflowtech.cupidcustomerapp.data.api.CupidApiService
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.response.LoginResponseData
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    private val apiService: CupidApiService
) {
    suspend fun login(
        loginRequestBody: LoginRequestBody
    ): CupidApiResponse<LoginResponseData> {
        return apiService.login(loginRequestBody)
    }
}