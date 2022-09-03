package com.smartflowtech.cupidcustomerapp.model

import kotlin.String

data class Transaction(
    val status: String?,
    val time: String?,
    val title: String?,
    val amount: String?,
    val date: String?,
    val authType: String?,
    val transactionSeqNumber: String?,
    val vendorStationName: String?,
    val product: String?
) {

    companion object {
//        val transactions = listOf(
//            Transaction(
//                "Completed",
//                "12:24AM",
//                "Transaction",
//                "₦67,000.00",
//                date = "2022-08-01",
//                authType = "Credit",
//                transactionSeqNumber = "TRS90399291",
//                vendorStationName = "Purchase on Cupid",
//                product = "PMS"
//            ),
//            Transaction(
//                "Pending",
//                "11:24PM",
//                "Transaction",
//                "₦30,000.00",
//                date = "2022-08-15",
//                authType = "Credit",
//                transactionSeqNumber = "TRS765599291",
//                vendorStationName = "Wallet Top-Up",
//                product = "PMS"
//            ),
//            Transaction(
//                "Completed",
//                "11:20AM",
//                "Transaction",
//                "₦12,000.00",
//                date = "2022-08-30",
//                authType = "Credit",
//                transactionSeqNumber = "TRS765599291",
//                vendorStationName = "Wallet Top-Up",
//                product = "AGO"
//            ),
//            Transaction(
//                "Failed",
//                "01:24PM",
//                "Transaction",
//                "₦17,000.00",
//                date = "2022-07-01",
//                authType = "Debit",
//                transactionSeqNumber = "TRS723599291",
//                vendorStationName = "Mobile Transfer",
//                product = "AGO"
//            ),
//            Transaction(
//                "Completed",
//                "11:24PM",
//                "Transaction",
//                "₦65,000.00",
//                date = "2022-07-15",
//                authType = "Debit",
//                transactionSeqNumber = "TRS0949482392",
//                vendorStationName = "Mobile Transfer",
//                product = "DPK"
//            ),
//            Transaction(
//                "Completed",
//                "11:24PM",
//                "Transaction",
//                "₦7,000.00",
//                date = "2022-08-07",
//                authType = "Debit",
//                transactionSeqNumber = "TRS501499291",
//                vendorStationName = "Purchase on Cupid",
//                product = "DPK"
//            ),
//            Transaction(
//                "Pending",
//                "08:24PM",
//                "Transaction",
//                "₦167,000.00",
//                date = "2022-08-21",
//                authType = "Credit",
//                transactionSeqNumber = "TRS501499291",
//                vendorStationName = "Wallet Top-Up",
//                product = "PMS"
//            ),
//            Transaction(
//                "Failed",
//                "11:24PM",
//                "Transaction",
//                "₦1,000.00",
//                date = "2022-08-24",
//                authType = "Debit",
//                transactionSeqNumber = "TRS97814937841",
//                vendorStationName = "Purchase on Cupid",
//                product = "AGO"
//            ),
//            Transaction(
//                "Completed",
//                "11:24PM",
//                "Transaction",
//                "₦10,000.00",
//                date = LocalDate.now().toString(),
//                authType = "Debit",
//                transactionSeqNumber = "TRS501499291",
//                vendorStationName = "Purchase on Cupid",
//                product = "DPK"
//            ),
//            Transaction(
//                "Pending",
//                "02:45PM",
//                "Transaction",
//                "₦99,000.00",
//                date = "2022-07-31",
//                authType = "Debit",
//                transactionSeqNumber = "TRS5011235591",
//                vendorStationName = "Purchase on Cupid",
//                product = "PMS"
//            ),
//            Transaction(
//                "Pending",
//                "11:24PM",
//                "Transaction",
//                "₦144,000.00",
//                date = "2022-08-01",
//                authType = "Credit",
//                transactionSeqNumber = "TRS504299291",
//                vendorStationName = "Cash Back",
//                product = "AGO"
//            ),
//            Transaction(
//                "Pending",
//                "05:30PM",
//                "Transaction",
//                "₦1.00",
//                date = "2022-01-01",
//                authType = "Debit",
//                transactionSeqNumber = "TRS3819499291",
//                vendorStationName = "Transaction Fee",
//                product = "DPK"
//            )
//
//        )
    }
}

