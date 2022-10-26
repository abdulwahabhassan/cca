package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class UpdateProfileRequestBody(
    @Json(name = "id")
    val id: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "username")
    val userName: String,
    @Json(name = "company_id")
    val companyId: String,
    @Json(name = "fullname")
    val fullName: String,
    @Json(name = "phone_number")
    val phoneNumber: String
)
