package com.smartflowtech.cupidcustomerapp.model.response

import com.squareup.moshi.Json


data class PayStackPaymentData(
    val ref: String,

    @Json(name = "company_wallet_id")
    val companyWalletID: Long,

    val status: String,

    @Json(name = "total_charged_amount")
    val totalChargedAmount: Long,

    @Json(name = "original_amount")
    val originalAmount: Long,

    @Json(name = "gateway_charged")
    val gatewayCharged: Double,

    @Json(name = "payment_cause")
    val paymentCause: String,

    @Json(name = "payment_mode_id")
    val paymentModeID: Long,

    @Json(name = "vendor_id")
    val vendorID: String,

    @Json(name = "wallet_amount")
    val walletAmount: Double,

    @Json(name = "initiating_users_name")
    val initiatingUsersName: String,

    @Json(name = "initiator_id")
    val initiatorID: Long,

    @Json(name = "updated_at")
    val updatedAt: String,

    @Json(name = "created_at")
    val createdAt: String,

    val id: Long,

    @Json(name = "amount_to_pay_kobo")
    val amountToPayKobo: Long,

    @Json(name = "paystack_subaccount_code")
    val paystackSubaccountCode: String,

    @Json(name = "access_code")
    val accessCode: String?
)