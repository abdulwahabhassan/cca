package com.smartflowtech.cupidcustomerapp.data.api

import com.smartflowtech.cupidcustomerapp.model.request.*
import com.smartflowtech.cupidcustomerapp.model.response.*
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
            LocalDate.now().plusDays(1).format(
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

    @POST("payments/paystack")
    suspend fun fundWalletAfterPayStackPayment(
        @Header("Authorization") token: String,
        @Body fundWalletRequestBody: FundWalletRequestBody
    ): CupidApiResponse<FundWalletData>

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

    @POST("add_device_token")
    suspend fun addDeviceToken(
        @Header("Authorization") token: String,
        @Body addDeviceTokenRequestBody: AddDeviceTokenRequestBody
    ): CupidApiResponse<DeviceTokenData>

    @GET("user_notification_history/{user_id}")
    suspend fun getNotifications(
        @Header("Authorization") token: String,
        @Path("user_id") userId: String,
    ): CupidApiResponse<GetNotificationsData>


    @POST("purchase_export")
    suspend fun getTransactionReport(
        @Header("Authorization") token: String,
        @Body transactionReportRequestBody: TransactionReportRequestBody
    ): CupidApiResponse<Any>

}