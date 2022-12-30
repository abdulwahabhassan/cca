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
import com.smartflowtech.cupidcustomerapp.ui.theme.*

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
    onGraphFilterClicked: (context: CardHistoryPeriodFilterContext, periods: List<String>) -> Unit,
    selectedMonthYearPeriod: String,
    cardHistoryPeriodFilterContext: CardHistoryPeriodFilterContext,
    onCompleteOnBoarding: () -> Unit,
    isOnBoarded: Boolean,
    refreshTransactionsAndWallets: () -> Unit
) {
    var letsGoButtonTapCount: Int by remember { mutableStateOf(0) }

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
                cardHistoryPeriodFilterContext = cardHistoryPeriodFilterContext,
                refreshTransactionsAndWallets = refreshTransactionsAndWallets
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
                        painter = painterResource(id = R.drawable.ic_no_data),
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
//                    val pagerState = rememberPagerState()
//                    HorizontalPager(
//                        count = 2,
//                        state = pagerState,
//                        contentPadding = PaddingValues(start = 16.dp, end = 32.dp),
//                        itemSpacing = 16.dp,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(bottom = 32.dp)
//                    ) { page: Int ->
//                        AdsCard(page)
//                    }
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
                        selectedCardNfcTagCode = selectedCardNfcTagCode,
                        refreshTransactionsAndWallets = refreshTransactionsAndWallets
                    )
                }
            }
        }
    }

    if (!isOnBoarded) {
        Dialog(
            onDismissRequest = { },
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(shape = RoundedCornerShape(8.dp), color = Color.White)
                    .padding(20.dp)

            ) {
                Text(
                    text = when (letsGoButtonTapCount) {
                        0 -> "Welcome to Cupid"
                        1 -> "Analytics"
                        2 -> "Customization"
                        else -> ""
                    },
                    color = darkBlue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (letsGoButtonTapCount) {
                        0 -> "Hi, I am Hassan from Cupid. Looks like you are new here. " +
                                "Let me show you around"
                        1 -> "Tap on your wallet card to view daily and monthly analytics " +
                                "of your transactions"
                        2 -> "Go into the 'Settings' to personalize your Cupid experience"
                        else -> ""
                    },
                    color = grey,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = when (letsGoButtonTapCount) {
                            0 -> "Let's go"
                            1 -> "Next"
                            2 -> "Finish"
                            else -> ""
                        },
                        color = darkBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .background(
                                shape = RoundedCornerShape(6.dp),
                                color = faintBlue
                            )
                            .clip(RoundedCornerShape(6.dp))
                            .clickable {
                                when (letsGoButtonTapCount) {
                                    0 -> letsGoButtonTapCount += 1
                                    1 -> letsGoButtonTapCount += 1
                                    2 -> onCompleteOnBoarding()
                                }
                            }
                            .padding(8.dp)
                    )

                    if (letsGoButtonTapCount != 2) {
                        Text(
                            text = "Skip",
                            color = darkBlue,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier
                                .background(
                                    shape = RoundedCornerShape(6.dp),
                                    color = Color.White
                                )
                                .clip(RoundedCornerShape(6.dp))
                                .clickable {
                                    onCompleteOnBoarding()
                                }
                                .padding(8.dp)
                        )
                    }
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

