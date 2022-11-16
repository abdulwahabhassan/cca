package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class FundWalletRequestBody(
    @Json(name = "paystack_payment")
    val payStackPayment: FundWallet
)

data class FundWallet(
    @Json(name = "payment_mode_id")
    val paymentModeID: Long,

    @Json(name = "company_id")
    val companyID: Long,

    @Json(name = "payment_uploaded_by")
    val paymentUploadedBy: String,

    @Json(name = "reference")
    val reference: String,

    @Json(name = "channel")
    val channel: String,

    @Json(name = "amount_paid")
    val amountPaid: Int

)
