package com.smartflowtech.cupidcustomerapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smartflowtech.cupidcustomerapp.model.domain.Transaction
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeEachWord

@Entity(tableName = "transaction")
data class TransactionEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long? = 0,
    @ColumnInfo(name = "status")
    val status: String?,
    @ColumnInfo(name = "time")
    val time: String?,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "amount")
    val amount: String?,
    @ColumnInfo(name = "date")
    val date: String?,
    @ColumnInfo(name = "auth_type")
    val authType: String?,
    @ColumnInfo(name = "transaction_seq_number")
    val transactionSeqNumber: String?,
    @ColumnInfo(name = "vendor_station_name")
    val vendorStationName: String?,
    @ColumnInfo(name = "product")
    val product: String?,
    @ColumnInfo(name = "nfc_tag_code")
    val nfcTagCode: String?,
    @ColumnInfo(name = "date_time")
    val dateTime: String?
) {
    fun mapToTransaction(): Transaction {
        return Transaction(
            id = this.id,
            status = this.status,
            time = this.time,
            title = this.title,
            amount = this.amount,
            date = this.date,
            authType = this.authType,
            transactionSeqNumber = this.transactionSeqNumber,
            vendorStationName = this.vendorStationName,
            product = this.product,
            nfcTagCode = this.nfcTagCode,
            dateTime = this.dateTime
        )
    }
}