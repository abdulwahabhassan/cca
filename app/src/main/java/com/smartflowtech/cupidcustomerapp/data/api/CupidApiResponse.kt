package com.smartflowtech.cupidcustomerapp.data.api

import com.squareup.moshi.Json

data class CupidApiResponse<T>(
    @Json(name = "status")
    var status: Boolean = false,
    @Json(name = "message")
    var message: String? = null,
    @Json(name = "editProfileResponse")
    val data: T? = null
)