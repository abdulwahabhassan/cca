package com.smartflowtech.cupidcustomerapp.model.response

import com.squareup.moshi.Json

data class TransactionResponseData(
    @Json(name = "id")
    val id: Long?,
)
