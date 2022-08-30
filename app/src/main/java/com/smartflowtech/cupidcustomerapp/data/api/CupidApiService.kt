package com.smartflowtech.cupidcustomerapp.data.api

import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.response.LoginResponseData
import com.smartflowtech.cupidcustomerapp.model.response.TransactionResponseData
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen
import retrofit2.http.*

interface CupidApiService {

    @POST("auth")
    suspend fun login(
        @Body loginRequestBody: LoginRequestBody
    ): CupidApiResponse<LoginResponseData>

    @GET("purchases")
    suspend fun getTransactions(
        @Query("company_id") companyId: String?,
        @Query("costcenter_id") costCenterId: String?,
        @Query("start_date") startDate: String?,
        @Query("end_date") endDate: String?,
        @Query("driver_id") driverId: String?,
        @Query("vendor_id") vendorId: String?,
    ): CupidApiResponse<List<TransactionResponseData>>
}