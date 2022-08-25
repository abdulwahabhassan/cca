package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import com.smartflowtech.cupidcustomerapp.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.SearchBar
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.TransactionDateHeader
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionsList(list: List<Transaction>) {

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {

        if (list.isEmpty()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier
                        .size(50.dp),
                    painter = painterResource(id = R.drawable.ic_no_transactions),
                    contentDescription = "Bottom sheet handle",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "You have no transaction history yet")
            }
        }

        val grouped = list.groupBy { it.date }


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
                        transaction
                    )
                }

            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TransactionsListPreview() {
    CupidCustomerAppTheme {
        TransactionsList(Transaction.transactions)
    }
}