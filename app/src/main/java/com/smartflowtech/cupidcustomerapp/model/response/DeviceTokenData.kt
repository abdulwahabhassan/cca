package com.smartflowtech.cupidcustomerapp.model.response

import com.squareup.moshi.Json

data class DeviceTokenData(
    @Json(name = "user_id")
    val id: String?,

    @Json(name = "device_token")
    val deviceToken: String?,
)
