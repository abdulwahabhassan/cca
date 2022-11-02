package com.smartflowtech.cupidcustomerapp.model.domain

import kotlin.String

data class Wallet(
    val vendorName: String,
    val vendorID: Long,
    val currentBalance: String,
    val updatedAt: String,
    val currentCreditLimit: String
)
