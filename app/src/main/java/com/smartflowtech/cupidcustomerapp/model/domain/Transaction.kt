package com.smartflowtech.cupidcustomerapp.model.domain

data class Transaction(
    val status: String?,
    val time: String?,
    val title: String?,
    val amount: String?,
    val date: String?,
    val authType: String?,
    val transactionSeqNumber: String?,
    val vendorStationName: String?,
    val product: String?,
    val nfcTagCode: String?,
    val dateTime: String? = "2022-01-01 12:00:00"
)