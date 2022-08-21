package com.smartflowtech.cupidcustomerapp.model

import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Transactions
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.Transaction

data class Transaction(
    val status: String,
    val time: String,
    val title: String,
    val amount: String,
    val date: String
) {
    companion object {
        val transactions = listOf(
            Transaction("Completed", "12:24AM", "Transaction", "₦167,000.00", date = "Jul 10, 2019"),
            Transaction("Completed", "11:24PM", "Transaction", "₦167,000.00", date = "Jun 24, 2018"),
            Transaction("Completed", "11:20AM", "Transaction", "₦167,000.00", date = "Jun 24, 2022"),
            Transaction("Completed", "01:24PM", "Transaction", "₦167,000.00", date = "Jul 24, 2022"),
            Transaction("Completed", "11:24PM", "Transaction", "₦167,000.00", date = "Feb 02, 2020"),
            Transaction("Completed", "11:24PM", "Transaction", "₦167,000.00", date = "Jul 24, 2022"),
            Transaction("Completed", "08:24PM", "Transaction", "₦167,000.00", date = "Mar 10, 2022"),
            Transaction("Completed", "11:24PM", "Transaction", "₦167,000.00", date = "Oct 02, 2021"),
            Transaction("Completed", "11:24PM", "Transaction", "₦167,000.00", date = "Jul 24, 2021"),
            Transaction("Completed", "02:45PM", "Transaction", "₦167,000.00", date = "Dec 24, 2022"),
            Transaction("Completed", "11:24PM", "Transaction", "₦167,000.00", date = "Jul 24, 2019"),
            Transaction("Completed", "05:30PM", "Transaction", "₦167,000.00", date = "Sep 15, 2022"),

        )
    }
}

