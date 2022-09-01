package com.smartflowtech.cupidcustomerapp.data.api

import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.model.response.LoginResponseData
import com.smartflowtech.cupidcustomerapp.model.response.TransactionsResponseData
import retrofit2.http.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface CupidApiService {

    @POST("auth")
    suspend fun login(
        @Body loginRequestBody: LoginRequestBody
    ): CupidApiResponse<LoginResponseData>


    @GET("purchases")
    suspend fun getTransactions(
        @Header("Authorization") token: String,
        @Query("company_id") companyId: String,
        @Query("start_date") startDate: String =
            LocalDate.now().minusDays(730).format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )
        ,
        @Query("end_date") endDate: String =
            LocalDate.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd")
        )
        ,
        @Query("vendor_id") vendorId: String = "",
    ): CupidApiResponse<List<TransactionsResponseData>>
}