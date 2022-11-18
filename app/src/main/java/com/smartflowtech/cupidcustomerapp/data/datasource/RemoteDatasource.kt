package com.smartflowtech.cupidcustomerapp.data.datasource

import com.smartflowtech.cupidcustomerapp.data.api.CupidApiResponse
import com.smartflowtech.cupidcustomerapp.data.api.CupidApiService
import com.smartflowtech.cupidcustomerapp.model.request.*
import com.smartflowtech.cupidcustomerapp.model.response.*
import javax.inject.Inject

class RemoteDatasource @Inject constructor(
    private val apiService: CupidApiService
) {
    suspend fun login(
        loginRequestBody: LoginRequestBody
    ): CupidApiResponse<LoginData> {
        return apiService.login(
            loginRequestBody = loginRequestBody
        )
    }

    suspend fun getTransactions(
        token: String,
        companyId: String
    ): CupidApiResponse<List<TransactionsData>> {
        return apiService.getTransactions(
            token = token,
            companyId = companyId
        )
    }

    suspend fun getWallets(
        token: String,
        companyId: String
    ): CupidApiResponse<List<WalletData>> {
        return apiService.getWallet(token = token, companyId = companyId)
    }

    suspend fun updateProfile(
        token: String,
        userId: String,
        updateProfileRequestBody: UpdateProfileRequestBody
    ): CupidApiResponse<UpdateProfileData> {
        return apiService.updateProfile(
            token = token,
            userId = userId,
            updateProfileRequestBody = updateProfileRequestBody
        )
    }

    suspend fun changePassword(
        token: String,
        userId: String,
        changePasswordRequestBody: ChangePasswordRequestBody
    ): CupidApiResponse<ChangePasswordData> {
        return apiService.changePassword(
            token = token,
            userId = userId,
            changePasswordRequestBody = changePasswordRequestBody
        )
    }

    suspend fun initiatePayStackPayment(
        token: String,
        payStackPaymentRequestBody: PayStackPaymentRequestBody
    ): CupidApiResponse<PayStackPaymentData> {
        return apiService.initiatePayStackPayment(
            token = token,
            payStackPaymentRequestBody = payStackPaymentRequestBody
        )
    }

    suspend fun fundWalletAfterPayStackPayment(
        token: String,
        fundWalletRequestBody: FundWalletRequestBody
    ): CupidApiResponse<FundWalletData> {
        return apiService.fundWalletAfterPayStackPayment(
            token = token,
            fundWalletRequestBody = fundWalletRequestBody
        )
    }

    suspend fun forgotPasswordVerifyEmail(
        token: String,
        verifyEmailRequestBody: VerifyEmailRequestBody
    ): CupidApiResponse<VerifyEmailData> {
        return apiService.forgotPassWordVerifyEmail(
            token = token,
            verifyEmailRequestBody = verifyEmailRequestBody
        )
    }

    suspend fun vendorStations(
        token: String,
        vendorId: Long
    ): CupidApiResponse<VendorStationsData> {
        return apiService.vendorStations(
            token = token,
            vendorId = vendorId
        )
    }

    suspend fun addDeviceToken(
        token: String,
        addDeviceTokenRequestBody: AddDeviceTokenRequestBody
    ): CupidApiResponse<DeviceTokenData> {
        return apiService.addDeviceToken(
            token = token,
            addDeviceTokenRequestBody = addDeviceTokenRequestBody
        )
    }

    suspend fun getNotifications(
        token: String,
        userId: String
    ): CupidApiResponse<GetNotificationsData> {
        return apiService.getNotifications(
            token = token,
            userId = userId
        )
    }

    suspend fun getTransactionReport(
        token: String,
        transactionReportRequestBody: TransactionReportRequestBody
    ): CupidApiResponse<Any> {
        return apiService.getTransactionReport(
            token = token,
            transactionReportRequestBody = transactionReportRequestBody
        )
    }
}