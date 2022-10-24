package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class VerifyEmailRequestBody(
    @Json(name = "hostname")
    val hostname: String,
    @Json(name = "type")
    val type: String,
    @Json(name = "email")
    val email: String

)
