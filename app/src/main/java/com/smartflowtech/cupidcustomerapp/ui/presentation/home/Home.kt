package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.CardTransactionHistory
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.Receipt
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.TransactionsList
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun Home(
    goToTransactions: () -> Unit,
    onBackPressed: () -> Unit,
    homeScreenUiState: HomeScreenUiState,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    getTransactions: () -> Unit,
    isCardSelected: Boolean,
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    currentBottomNavDestination: String
) {
    val pagerState = rememberPagerState()

    BackHandler {
        onBackPressed()
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

    if (isCardSelected) {
        CardTransactionHistory(
            homeScreenUiState = homeScreenUiState,
            bottomSheetScaffoldState = bottomSheetScaffoldState,
            onSelectTransaction = { transaction ->
                selectedTransaction = transaction
                showReceipt = true
            },
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            currentBottomNavDestination = currentBottomNavDestination,
//            transactions = homeScreenUiState.transactions
//                .filter { transaction: Transaction ->
//                transaction.nfcTagCode
//            }
        )
    } else {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start
        ) {
            HorizontalPager(
                count = 2,
                state = pagerState,
                contentPadding = PaddingValues(start = 16.dp, end = 32.dp),
                itemSpacing = 16.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            ) { page: Int ->
                AdsCard(page)
            }
            Row(
                modifier = Modifier
                    .padding(start = 16.dp, end = 4.dp)
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
                IconButton(onClick = { goToTransactions() }, enabled = false) {
//                    Icon(
//                        imageVector = Icons.Rounded.ArrowForward,
//                        contentDescription = "Forward arrow",
//                        tint = black
//                    )
                }
            }

            TransactionsList(
                homeScreenUiState = homeScreenUiState,
                onSelectTransaction = { transaction ->
                    selectedTransaction = transaction
                    showReceipt = true
                },
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                getTransactions = getTransactions,
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
                    Receipt(
                        transaction = selectedTransaction,
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
fun HomePreview() {
    //Home ({})
}

