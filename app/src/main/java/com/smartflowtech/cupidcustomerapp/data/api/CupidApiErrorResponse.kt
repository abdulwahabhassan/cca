package com.smartflowtech.cupidcustomerapp.data.api

import com.squareup.moshi.Json

data class CupidApiErrorResponse(
    @Json(name = "status")
    var status: Boolean = false,
    @Json(name = "message")
    var message: String? = null,
)