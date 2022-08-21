package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.Transaction
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.pink

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Transactions() {
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {

        //No transaction history column
//        Column(
//            Modifier
//                .fillMaxWidth()
//                .fillMaxHeight(0.5f),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Icon(
//                modifier = Modifier
//                    .size(80.dp),
//                painter = painterResource(id = R.drawable.ic_no_transactions),
//                contentDescription = "Bottom sheet handle",
//                tint = Color.Unspecified
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            Text(text = "You have no transaction history yet")
//        }

        val grouped =
            com.smartflowtech.cupidcustomerapp.model.Transaction.transactions.groupBy { it.date }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            grouped.forEach { (date, transactions) ->
                stickyHeader {
                    TransactionDateHeader(date)
                }

                items(transactions) { transaction ->
                    Transaction(
                        transaction.status,
                        transaction.time,
                        transaction.title,
                        transaction.amount
                    )
                }

            }
        }
    }
}

    @Composable
    @Preview(showBackground = true)
    fun TransactionsPreview() {
        CupidCustomerAppTheme {
            Transactions()
        }
    }