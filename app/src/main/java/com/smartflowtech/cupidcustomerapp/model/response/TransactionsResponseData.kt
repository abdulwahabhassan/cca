package com.smartflowtech.cupidcustomerapp.model.response

import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeEachWord
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter
import com.squareup.moshi.Json

data class TransactionsResponseData(
    val id: Long?,

    @Json(name = "vendor_id")
    val vendorID: Long?,

    @Json(name = "company_id")
    val companyID: String?,

    @Json(name = "costcenter_id")
    val costcenterID: String?,

    @Json(name = "vendor_station_name")
    val vendorStationName: String?,

    @Json(name = "vehicle_plate_number")
    val vehiclePlateNumber: String?,

    @Json(name = "auth_type")
    val authType: String?,

    @Json(name = "nfctag_id")
    val nfctagID: String?,

    @Json(name = "driver_id")
    val driverID: String?,

    val volume: String?,

    val product: String?,

    @Json(name = "created_at")
    val createdAt: String?,

    @Json(name = "last_volume_dispensed")
    val lastVolumeDispensed: String?,

    @Json(name = "last_amount_paid")
    val lastAmountPaid: String?,

    @Json(name = "is_balanced")
    val isBalanced: String?,

    @Json(name = "verified_volume")
    val verifiedVolume: String?,

    @Json(name = "verified_amount")
    val verifiedAmount: String?,

    @Json(name = "transaction_seq_no")
    val transactionSeqNumber: String?,

    val costcenter: Costcenter?,
    val driver: Driver?,
    val nfctag: Nfctag?,
    val vendor: Vendor?
) {
    fun mapToTransaction(): Transaction {
        return Transaction(
            status = when (this.isBalanced) {
                "1" -> "Completed"
                "0" -> "Failed"
                else -> "Pending"
            },
            time = this.createdAt?.substring(11),
            title = this.product + ", " + this.vendorStationName?.capitalizeEachWord(),
            amount = this.verifiedAmount,
            date = this.createdAt?.substring(0, 10),
            authType = this.authType,
            transactionSeqNumber = this.transactionSeqNumber ?: this.id.toString(),
            vendorStationName = this.vendorStationName,
            product = this.product,
            nfcTagCode = this.nfctag?.nfctagCode,
            dateTime = this.createdAt
        )
    }
}

data class Costcenter(
    val id: Long,
    val name: String
)

data class Vendor(
    val id: Long,
    val name: String
)

data class Nfctag(
    val id: Long,
    @Json(name = "nfctag_code")
    val nfctagCode: String
)

data class Driver(
    val id: Long,
    val fullname: String
)


