package com.smartflowtech.cupidcustomerapp.data.api

import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.response.LoginResponseData
import retrofit2.http.*

interface CupidApiService {

    @POST("auth")
    suspend fun login(
        @Body loginRequestBody: LoginRequestBody
    ): CupidApiResponse<LoginResponseData>

}