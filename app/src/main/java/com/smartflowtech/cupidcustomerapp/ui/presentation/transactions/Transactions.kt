package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.SearchBar
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.black

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Transactions(
    downloadTransactions: () -> Unit,
    onSearchBarClicked: () -> Unit,
    onBackPressed: () -> Unit,
    transactions: List<Transaction>,
    bottomSheetState: BottomSheetState
) {

    var queryText by rememberSaveable { mutableStateOf("") }
    var selectedTransaction: Transaction by remember {
        mutableStateOf(
            Transaction(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        )
    }

    var showReceipt: Boolean by remember {
        mutableStateOf(false)
    }

    BackHandler(true) {
        onBackPressed()
    }

    if (showReceipt) {
        Receipt(
            transaction = selectedTransaction,
            onGoBackToTransactionListPressed = {
                showReceipt = false
            }
        )
    } else {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start
        ) {

            SearchBar(
                query = queryText,
                onQueryChange = { searchText ->
                    queryText = searchText
                },
                onSearchBarClicked = onSearchBarClicked
            )

            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 4.dp, top = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Transaction History",
                    color = Color.Black,
                    fontFamily = AthleticsFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                IconButton(onClick = { downloadTransactions() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_download),
                        contentDescription = "Forward arrow",
                        tint = black
                    )
                }
            }

            TransactionsList(
                transactions = transactions,
                onTransactionClicked = { transaction: Transaction ->
                    showReceipt = true
                    selectedTransaction = transaction
                },
                bottomSheetState = bottomSheetState
            )

        }
    }


}

@Composable
@Preview(showBackground = true)
fun TransactionsPreview() {
    CupidCustomerAppTheme {
        //Transactions({}, {}, {}, Transaction.transactions)
    }
}