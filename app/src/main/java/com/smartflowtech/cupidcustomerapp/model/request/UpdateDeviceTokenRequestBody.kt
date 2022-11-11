package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class UpdateDeviceTokenRequestBody (
    @Json(name = "customer_id")
    val customerId: String,
    @Json(name = "device_token")
    val deviceToken: String
)