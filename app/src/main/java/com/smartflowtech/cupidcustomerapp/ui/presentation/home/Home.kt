package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.HorizontalRule
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.CardHistoryPeriodFilterContext
import com.smartflowtech.cupidcustomerapp.model.domain.Transaction
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.CardTransactionHistory
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.Receipt
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.TransactionsList
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.grey

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun Home(
    goToTransactions: () -> Unit,
    onBackPressed: () -> Unit,
    homeScreenUiState: HomeScreenUiState,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    isCardSelected: Boolean,
    selectedCardNfcTagCode: String,
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    currentBottomNavDestination: String,
    bottomNavBarNavHostController: NavHostController,
    bottomSheetState: BottomSheetState,
    onBottomNavItemClicked: (String) -> Unit,
    onGraphFilterClicked:  (context: CardHistoryPeriodFilterContext, periods: List<String>) -> Unit,
    selectedMonthYearPeriod: String,
    cardHistoryPeriodFilterContext: CardHistoryPeriodFilterContext
) {
    val pagerState = rememberPagerState()

    BackHandler {
        onBackPressed()
    }

    var showReceipt: Boolean by remember {
        mutableStateOf(false)
    }

    var selectedTransaction: Transaction? by remember {
        mutableStateOf(
            null
        )
    }

    Column(
        Modifier
            .fillMaxSize()
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            modifier = Modifier
                .size(46.dp)
                .padding(bottom = 16.dp)
                .clip(RoundedCornerShape(50))
                .clipToBounds()
                .clickable {
                    if (
                        bottomNavBarNavHostController.currentDestination?.route ==
                        com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Home.route
                    ) {
                        onBottomNavItemClicked(com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Transactions.route)
                    } else {
                        onBottomNavItemClicked(com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Home.route)
                    }
                }
                .align(Alignment.CenterHorizontally),
            imageVector = if (bottomSheetState.isExpanded)
                Icons.Rounded.HorizontalRule
            else
                Icons.Rounded.HorizontalRule,
            contentDescription = "Bottom sheet handle",
            tint = grey
        )

        if (isCardSelected) {
            CardTransactionHistory(
                homeScreenUiState = homeScreenUiState,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                onSelectTransaction = { transaction ->
                    selectedTransaction = transaction
                    showReceipt = true
                },
                selectedCardNfcTagCode = selectedCardNfcTagCode,
                selectedTab = selectedTab,
                onTabSelected = onTabSelected,
                currentBottomNavDestination = currentBottomNavDestination,
                onGraphFilterClicked = onGraphFilterClicked,
                selectedMonthYearPeriod = selectedMonthYearPeriod,
                cardHistoryPeriodFilterContext = cardHistoryPeriodFilterContext
            )
        } else {
            if (homeScreenUiState.transactions.isEmpty()) {
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
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .size(50.dp),
                        painter = painterResource(id = R.drawable.ic_no_transactions),
                        contentDescription = "No transactions icon",
                        tint = Color.Unspecified
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "You have no transaction history yet")
                }
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
                        IconButton(onClick = { goToTransactions() }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowForward,
                                contentDescription = "Forward arrow",
                                tint = Color.Black
                            )
                        }
                    }

                    TransactionsList(
                        homeScreenUiState = homeScreenUiState,
                        onSelectTransaction = { transaction ->
                            selectedTransaction = transaction
                            showReceipt = true
                        },
                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                        currentBottomNavDestination = currentBottomNavDestination,
                        selectedCardNfcTagCode = selectedCardNfcTagCode
                    )
                }
            }
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

