package com.smartflowtech.cupidcustomerapp.model.response

import com.squareup.moshi.Json

data class VendorBankAccountData(
    val id: Long,

    @Json(name = "account_number")
    val accountNumber: String,

    @Json(name = "bank_name")
    val bankName: String,

    @Json(name = "account_name")
    val accountName: String,

    @Json(name = "paystack_id")
    val paystackID: String,

    @Json(name = "paystack_subaccount_code")
    val paystackSubaccountCode: String,

    @Json(name = "vendor_id")
    val vendorID: String,

    val active: String,

    @Json(name = "created_at")
    val createdAt: String,

    @Json(name = "updated_at")
    val updatedAt: String,

    @Json(name = "payment_mode")
    val paymentMode: String,

    val merchantcodes: Any? = null
)
