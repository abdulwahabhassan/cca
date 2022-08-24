package com.smartflowtech.cupidcustomerapp.model.response

import com.squareup.moshi.Json

class LoginResponseData(
    @Json(name = "id")
    val id: Long?,
    @Json(name = "username")
    val username: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "fullname")
    val fullname: String?,
)
