package com.smartflowtech.cupidcustomerapp.model.response

import com.squareup.moshi.Json

data class VerifyEmailData(
    @Json(name = "id")
    val id: Long,

    @Json(name = "fullname")
    val fullname: String,

    @Json(name = "email")
    val email: String,

    @Json(name = "username")
    val username: String,

    @Json(name = "is_company_set_up")
    val isCompanySetUp: Boolean?,

    @Json(name = "phone_number")
    val phoneNumber: String,

    @Json(name = "is_verified")
    val isVerified: Long,

    @Json(name = "company_id")
    val companyID: String,

    @Json(name = "status")
    val status: String,

    @Json(name = "created_at")
    val createdAt: String?,

    @Json(name = "updated_at")
    val updatedAt: String?,

    @Json(name = "deleted_at")
    val deletedAt: String?,

    @Json(name = "first_name")
    val firstName: String,

    @Json(name = "last_name")
    val lastName: String
)