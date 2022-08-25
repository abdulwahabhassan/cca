package com.smartflowtech.cupidcustomerapp.model

data class Transaction(
    val status: String,
    val time: String,
    val title: String,
    val amount: String,
    val date: String,
    val type: String
) {
    companion object {
        val transactions = listOf(
            Transaction(
                "Completed",
                "12:24AM",
                "Transaction",
                "₦67,000.00",
                date = "Jul 10, 2019",
                type = "Credit"
            ),
            Transaction(
                "Pending",
                "11:24PM",
                "Transaction",
                "₦30,000.00",
                date = "Jun 24, 2018",
                type = "Credit"
            ),
            Transaction(
                "Completed",
                "11:20AM",
                "Transaction",
                "₦12,000.00",
                date = "Jun 24, 2022",
                type = "Credit"
            ),
            Transaction(
                "Failed",
                "01:24PM",
                "Transaction",
                "₦17,000.00",
                date = "Jul 24, 2022",
                type = "Credit"
            ),
            Transaction(
                "Completed",
                "11:24PM",
                "Transaction",
                "₦65,000.00",
                date = "Feb 02, 2020",
                type = "Debit"
            ),
            Transaction(
                "Completed",
                "11:24PM",
                "Transaction",
                "₦7,000.00",
                date = "Jul 24, 2022",
                type = "Credit"
            ),
            Transaction(
                "Pending",
                "08:24PM",
                "Transaction",
                "₦167,000.00",
                date = "Mar 10, 2022",
                type = "Debit"
            ),
            Transaction(
                "Failed",
                "11:24PM",
                "Transaction",
                "₦1,000.00",
                date = "Oct 02, 2021",
                type = "Debit"
            ),
            Transaction(
                "Completed",
                "11:24PM",
                "Transaction",
                "₦10,000.00",
                date = "Jul 24, 2021",
                type = "Credit"
            ),
            Transaction(
                "Pending",
                "02:45PM",
                "Transaction",
                "₦99,000.00",
                date = "Dec 24, 2022",
                type = "Debit"
            ),
            Transaction(
                "Pending",
                "11:24PM",
                "Transaction",
                "₦144,000.00",
                date = "Jul 24, 2019",
                type = "Credit"
            ),
            Transaction(
                "Pending",
                "05:30PM",
                "Transaction",
                "₦57,000.00",
                date = "Sep 15, 2022",
                type = "Debit"
            ),

            )
    }
}

