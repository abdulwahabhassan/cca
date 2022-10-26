package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class LoginRequestBody(
    @Json(name = "email")
    val email: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "type")
    val type: String = "FLEET_MANAGER",
    @Json(name = "os_version")
    val osVersion: String = "Android",
    @Json(name = "hostname")
    val hostname: String = "www.cupid.smartflowtech.com",
)