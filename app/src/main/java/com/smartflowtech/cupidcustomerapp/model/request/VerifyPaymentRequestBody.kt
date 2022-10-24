package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class VerifyPaymentRequestBody(
    @Json(name = "paystack_payment")
    val payments: List<PayStackPayment>
)

data class PayStackPayment(
    @Json(name = "payment_mode_id")
    val paymentModeID: String?,

    @Json(name = "vendor_id")
    val vendorID: Long?,

    @Json(name = "company_id")
    val companyID: Long?,

    @Json(name = "amount_to_pay")
    val amountToPay: Long?
)
