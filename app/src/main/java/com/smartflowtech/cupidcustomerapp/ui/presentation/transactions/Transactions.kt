package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.window.Dialog
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.SearchBar
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.black

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Transactions(
    onDownloadTransactionsClicked: () -> Unit,
    onSearchBarClicked: () -> Unit,
    onBackPressed: () -> Unit,
    homeScreenUiState: HomeScreenUiState,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    isCardSelected: Boolean,
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    currentBottomNavDestination: String
) {

    var queryText by rememberSaveable { mutableStateOf("") }

    val filteredHomeScreenUiState by remember(queryText, homeScreenUiState) {
        val list = homeScreenUiState.transactions.filter { transaction ->
            (transaction.status?.contains(queryText, true) == true) ||
                    (transaction.amount?.contains(queryText, true) == true) ||
                    transaction.date?.contains(queryText, true) == true ||
                    (transaction.time?.contains(queryText, true) == true) ||
                    transaction.authType?.contains(queryText, true) == true ||
                    transaction.title?.contains(queryText, true) == true ||
                    transaction.vendorStationName?.contains(queryText, true) == true ||
                    transaction.transactionSeqNumber?.contains(queryText, true) == true
        }
        mutableStateOf(
            HomeScreenUiState(
                viewModelResult = homeScreenUiState.viewModelResult,
                transactions = list,
                wallets = homeScreenUiState.wallets
            )
        )
    }

    var showReceipt: Boolean by remember {
        mutableStateOf(false)
    }

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
                "",
                ""
            )
        )
    }

    BackHandler(true) {
        onBackPressed()
    }

    if (isCardSelected) {
        CardTransactionHistory(
            homeScreenUiState = homeScreenUiState,
            onSelectTransaction = { transaction ->
                selectedTransaction = transaction
                showReceipt = true
            },
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            currentBottomNavDestination = currentBottomNavDestination
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
                onSearchBarClicked = onSearchBarClicked,
                "Search transactions"
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
                IconButton(onClick = { onDownloadTransactionsClicked() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_download),
                        contentDescription = "Download icon",
                        tint = black
                    )
                }
            }

            TransactionsList(
                homeScreenUiState = filteredHomeScreenUiState,
                onSelectTransaction = { transaction: Transaction ->
                    selectedTransaction = transaction
                    showReceipt = true
                },
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                getTransactions = {},
                currentBottomNavDestination = currentBottomNavDestination
            )


        }
    }

    if (showReceipt) {
        Dialog(
            onDismissRequest = { showReceipt = false },
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(top = 32.dp)

            ) {
                item {
                    Receipt(transaction = selectedTransaction,
                        onGoBackToTransactionListPressed = {
                            showReceipt = false
                        }
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
        //Transactions({}, {}, {}, Transaction.transactions)
    }
}