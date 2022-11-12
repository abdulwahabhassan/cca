package com.smartflowtech.cupidcustomerapp.model.response

import com.squareup.moshi.Json

class LoginData(
    @Json(name = "id")
    val id: Long?,

    @Json(name = "email")
    val email: String?,

    @Json(name = "fullname")
    val fullname: String?,

    @Json(name = "first_name")
    val firstname: String?,

    @Json(name = "last_name")
    val lastname: String?,

    @Json(name = "status")
    val status: String?,

    @Json(name = "is_verified")
    val isVerified: Long?,

    @Json(name = "is_company_set_up")
    val isCompanySetUp: Any?,

    @Json(name = "username")
    val username: String?,

    @Json(name = "phone_number")
    val phoneNumber: String?,

    @Json(name = "vendor_id")
    val vendorId: Long?,

    @Json(name = "token")
    val token: String?,

    @Json(name = "company_id")
    val companyId: String?,

    @Json(name = "created_at")
    val createdAt: String?,

    @Json(name = "updated_at")
    val updatedAt: String?,

    @Json(name = "deleted_at")
    val deletedAt: String?,

    @Json(name = "bank_account")
    val vendorBankAccountData: VendorBankAccountData?,

    )
