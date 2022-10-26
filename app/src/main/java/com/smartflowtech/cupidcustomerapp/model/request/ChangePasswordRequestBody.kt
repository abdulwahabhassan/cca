package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class ChangePasswordRequestBody(
    @Json(name = "id")
    val id: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "username")
    val userName: String,
    @Json(name = "company_id")
    val companyId: String,
    @Json(name = "currentpassword")
    val currentPassword: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "repeatPassword")
    val repeatPassword: String,
)
