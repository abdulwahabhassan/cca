package com.smartflowtech.cupidcustomerapp.model.domain

import com.squareup.moshi.Json

data class CompanyUser(
    //Mandatory for change password and update profile
    @Json(name = "id")
    val id: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "username")
    val userName: String,
    @Json(name = "company_id")
    val companyId: String,

    //Required for change password
    @Json(name = "currentpassword")
    val currentPassword: String? = null,
    @Json(name = "password")
    val password: String? = null,
    @Json(name = "repeatPassword")
    val repeatPassword: String? = null,

    //Required for update profile
    @Json(name = "fullname")
    val fullName: String? = null,
    @Json(name = "phone_number")
    val phoneNumber: String? = null
)
