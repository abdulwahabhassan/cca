package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json


data class PayStackPaymentRequestBody(
    @Json(name = "paystack_payment")
    val payStackPayment: PayStackPayment
)

data class PayStackPayment(
    @Json(name = "payment_mode_id")
    val paymentModeID: Long,

    @Json(name = "company_id")
    val companyID: Long,

    @Json(name = "payment_initated_by")
    val paymentInitiatedBy: String,

    @Json(name = "amount_to_pay")
    val amountToPay: Int
)
