package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class UpdateProfileRequestBody(
    @Json(name = "id")
    val id: String,
    @Json(name = "fullname")
    val fullName: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "username")
    val userName: String,
    @Json(name = "is_company_set_up")
    val isCompanySetUp: String? = null,
    @Json(name = "phone_number")
    val phoneNumber: String,
    @Json(name = "is_verified")
    val isVerified: Long,
    @Json(name = "company_id")
    val companyId: String,
    @Json(name = "status")
    val status: String,
    @Json(name = "created_at")
    val createdAt: String? = null,
    @Json(name = "updated_at")
    val updatedAt: String? = null,
    @Json(name = "deleted_at")
    val deletedAt: String? = null,
    @Json(name = "first_name")
    val firstName: String? = null,
    @Json(name = "last_name")
    val lastName: String? = null,
    @Json(name = "token")
    val token: String,
    @Json(name = "currentpassword")
    val currentPassword: String,
    @Json(name = "password")
    val password: String,
    @Json(name = "repeatPassword")
    val repeatPassword: String,
)
