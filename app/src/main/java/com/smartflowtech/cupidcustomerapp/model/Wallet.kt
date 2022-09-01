package com.smartflowtech.cupidcustomerapp.model

import com.squareup.moshi.Json

data class Wallet(
    val vendorName: String,
    val vendorID: Long,
    val currentBalance: String,
    val updatedAt: String,
    val currentCreditLimit: String
)
