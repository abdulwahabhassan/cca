package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class VerifyEmailRequestBody(
    @Json(name = "hostname")
    val hostname: String = "www.cupid.smartflowtech.com",
    @Json(name = "type")
    val type: String = "FLEET_MANAGER",
    @Json(name = "email")
    val email: String

)
