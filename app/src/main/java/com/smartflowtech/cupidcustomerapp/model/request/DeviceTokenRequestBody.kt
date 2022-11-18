package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class AddDeviceTokenRequestBody (
    @Json(name = "user_id")
    val customerId: String,
    @Json(name = "device_token")
    val deviceToken: String
)