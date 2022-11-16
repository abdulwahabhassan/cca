package com.smartflowtech.cupidcustomerapp.model.response

import com.smartflowtech.cupidcustomerapp.model.domain.Wallet
import com.squareup.moshi.Json

data class WalletData(
    @Json(name = "vendor_name")
    val vendorName: String,

    @Json(name = "vendor_id")
    val vendorID: Long?,

    @Json(name = "current_balance")
    val currentBalance: String?,

    @Json(name = "updated_at")
    val updatedAt: String?,

    @Json(name = "current_credit_limit")
    val currentCreditLimit: String?,

    @Json(name = "nfctags_id")
    val nfcTagId: Long?,

    @Json(name = "nfctags_code")
    val nfcTagCode: String?
) {
    fun mapToWallet(): Wallet {
        return Wallet(
            vendorName = this.vendorName,
            vendorID = this.vendorID,
            currentBalance = this.currentBalance,
            updatedAt = this.updatedAt,
            currentCreditLimit = this.currentCreditLimit,
            nfcTagId = this.nfcTagId,
            nfcTagCode = this.nfcTagCode
        )
    }
}


