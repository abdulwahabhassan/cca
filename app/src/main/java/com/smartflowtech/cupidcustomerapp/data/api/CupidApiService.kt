package com.smartflowtech.cupidcustomerapp.data.api

import com.smartflowtech.cupidcustomerapp.model.request.*
import com.smartflowtech.cupidcustomerapp.model.response.*
import com.squareup.moshi.Json
import retrofit2.http.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

interface CupidApiService {

    @POST("auth")
    suspend fun login(
        @Body loginRequestBody: LoginRequestBody
    ): CupidApiResponse<LoginData>

    @GET("purchases")
    suspend fun getTransactions(
        @Header("Authorization") token: String,
        @Query("company_id") companyId: String,
        @Query("start_date") startDate: String =
            LocalDate.now().minusDays(730).format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            ),
        @Query("end_date") endDate: String =
            LocalDate.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            ),
        @Query("vendor_id") vendorId: String = "",
    ): CupidApiResponse<List<TransactionsData>>

    @GET("wallets")
    suspend fun getWallet(
        @Header("Authorization") token: String,
        @Query("company_id") companyId: String
    ): CupidApiResponse<List<WalletData>>

    @PATCH("company_users/{user_id}/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Path("user_id") userId: String,
        @Body updateProfileRequestBody: UpdateProfileRequestBody
    ): CupidApiResponse<UpdateProfileData>

    @PATCH("company_users/{user_id}/profile")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Path("user_id") userId: String,
        @Body changePasswordRequestBody: ChangePasswordRequestBody
    ): CupidApiResponse<ChangePasswordData>

    @POST("payments/initiate/paystack")
    suspend fun initiatePayStackPayment(
        @Header("Authorization") token: String,
        @Body payStackPaymentRequestBody: PayStackPaymentRequestBody
    ): CupidApiResponse<PayStackPaymentData>

    @POST("auth/forgotpass/verifyemail")
    suspend fun forgotPassWordVerifyEmail(
        @Header("Authorization") token: String,
        @Body verifyEmailRequestBody: VerifyEmailRequestBody
    ): CupidApiResponse<VerifyEmailData>

    @GET("sm_stations/{vendor_id}")
    suspend fun vendorStations(
        @Header("Authorization") token: String,
        @Path("vendor_id") vendorId: Long,
    ): CupidApiResponse<VendorStationsData>

    @PATCH("update_device_token")
    suspend fun updateDeviceToken(
        @Header("Authorization") token: String,
        @Body updateDeviceTokenRequestBody: UpdateDeviceTokenRequestBody
    ): CupidApiResponse<Any>

    @POST("purchase_export")
    suspend fun getTransactionReport(
        @Header("Authorization") token: String,
        @Body transactionReportRequestBody: TransactionReportRequestBody
    ): CupidApiResponse<Any>

}