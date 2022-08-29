package com.smartflowtech.cupidcustomerapp.model.response

import com.squareup.moshi.Json

class LoginResponseData(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "fullname")
    val fullname: String?,
    @Json(name = "phone_number")
    val phoneNumber: String?,
    @Json(name = "token")
    val token: String?,
)
