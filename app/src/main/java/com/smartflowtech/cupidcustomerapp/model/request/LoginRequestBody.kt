package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class LoginRequestBody(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String
)