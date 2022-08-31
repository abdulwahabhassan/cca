package com.smartflowtech.cupidcustomerapp.model

import java.time.LocalDate

data class Transaction(
    val status: String,
    val time: String,
    val title: String,
    val amount: String,
    val date: String,
    val type: String,
    val referenceNumber: String,
    val description: String,
    val product: String
) {

    companion object {
        val transactions = listOf(
            Transaction(
                "Completed",
                "12:24AM",
                "Transaction",
                "₦67,000.00",
                date = "2022-08-01",
                type = "Credit",
                referenceNumber = "TRS90399291",
                description = "Purchase on Cupid",
                product = "PMS"
            ),
            Transaction(
                "Pending",
                "11:24PM",
                "Transaction",
                "₦30,000.00",
                date = "2022-08-15",
                type = "Credit",
                referenceNumber = "TRS765599291",
                description = "Wallet Top-Up",
                product = "PMS"
            ),
            Transaction(
                "Completed",
                "11:20AM",
                "Transaction",
                "₦12,000.00",
                date = "2022-08-30",
                type = "Credit",
                referenceNumber = "TRS765599291",
                description = "Wallet Top-Up",
                product = "AGO"
            ),
            Transaction(
                "Failed",
                "01:24PM",
                "Transaction",
                "₦17,000.00",
                date = "2022-07-01",
                type = "Debit",
                referenceNumber = "TRS723599291",
                description = "Mobile Transfer",
                product = "AGO"
            ),
            Transaction(
                "Completed",
                "11:24PM",
                "Transaction",
                "₦65,000.00",
                date = "2022-07-15",
                type = "Debit",
                referenceNumber = "TRS0949482392",
                description = "Mobile Transfer",
                product = "DPK"
            ),
            Transaction(
                "Completed",
                "11:24PM",
                "Transaction",
                "₦7,000.00",
                date = "2022-08-07",
                type = "Debit",
                referenceNumber = "TRS501499291",
                description = "Purchase on Cupid",
                product = "DPK"
            ),
            Transaction(
                "Pending",
                "08:24PM",
                "Transaction",
                "₦167,000.00",
                date = "2022-08-21",
                type = "Credit",
                referenceNumber = "TRS501499291",
                description = "Wallet Top-Up",
                product = "PMS"
            ),
            Transaction(
                "Failed",
                "11:24PM",
                "Transaction",
                "₦1,000.00",
                date = "2022-08-24",
                type = "Debit",
                referenceNumber = "TRS97814937841",
                description = "Purchase on Cupid",
                product = "AGO"
            ),
            Transaction(
                "Completed",
                "11:24PM",
                "Transaction",
                "₦10,000.00",
                date = LocalDate.now().toString(),
                type = "Debit",
                referenceNumber = "TRS501499291",
                description = "Purchase on Cupid",
                product = "DPK"
            ),
            Transaction(
                "Pending",
                "02:45PM",
                "Transaction",
                "₦99,000.00",
                date = "2022-07-31",
                type = "Debit",
                referenceNumber = "TRS5011235591",
                description = "Purchase on Cupid",
                product = "PMS"
            ),
            Transaction(
                "Pending",
                "11:24PM",
                "Transaction",
                "₦144,000.00",
                date = "2022-08-01",
                type = "Credit",
                referenceNumber = "TRS504299291",
                description = "Cash Back",
                product = "AGO"
            ),
            Transaction(
                "Pending",
                "05:30PM",
                "Transaction",
                "₦1.00",
                date = "2022-01-01",
                type = "Debit",
                referenceNumber = "TRS3819499291",
                description = "Transaction Fee",
                product = "DPK"
            )

        )
    }
}

