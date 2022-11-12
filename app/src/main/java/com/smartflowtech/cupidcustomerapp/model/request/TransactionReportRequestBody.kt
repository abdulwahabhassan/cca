package com.smartflowtech.cupidcustomerapp.model.request

import com.squareup.moshi.Json

data class TransactionReportRequestBody(
    @Json(name = "from_date")
    val dateFrom: String,

    @Json(name = "to_date")
    val dateTo: String,

    @Json(name = "company_id")
    val companyId: Long,

    @Json(name = "email")
    val email: String
)
