package com.smartflowtech.cupidcustomerapp.model.request

import com.smartflowtech.cupidcustomerapp.model.CompanyUser
import com.squareup.moshi.Json

data class UpdateProfileRequestBody(
    @Json(name = "company_user")
    val companyUser: CompanyUser
)
