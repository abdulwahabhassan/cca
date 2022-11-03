package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import com.smartflowtech.cupidcustomerapp.R
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.model.domain.Transaction
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Header
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.HomeScreenUiState
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun TransactionsList(
    homeScreenUiState: HomeScreenUiState,
    onSelectTransaction: (transaction: Transaction) -> Unit,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    currentBottomNavDestination: String
) {

    when (homeScreenUiState.viewModelResult) {
        ViewModelResult.LOADING -> {
            if (currentBottomNavDestination != HomeScreen.Home.route) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedVisibility(
                        visible = (bottomSheetScaffoldState.bottomSheetState.isCollapsed ||
                                bottomSheetScaffoldState.bottomSheetState.isExpanded) &&
                                !bottomSheetScaffoldState.bottomSheetState.isAnimationRunning,
                        enter = slideInVertically(spring()) { it / 10000 },
                        exit = slideOutVertically(spring()) { it / 10000 }
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) 0.2f else 0.7f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(strokeWidth = 2.dp)
                        }
                    }
                }
            }

        }
        ViewModelResult.INITIAL -> {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start
            ) {}
        }
        ViewModelResult.ERROR -> {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f),
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
            }

        }
        ViewModelResult.SUCCESS -> {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.Start
            ) {

                if (homeScreenUiState.transactions.isEmpty()) {
                    AnimatedVisibility(
                        visible = (bottomSheetScaffoldState.bottomSheetState.isCollapsed ||
                                bottomSheetScaffoldState.bottomSheetState.isExpanded) &&
                                !bottomSheetScaffoldState.bottomSheetState.isAnimationRunning,
                        enter = slideInVertically(spring()) { it / 10000 },
                        exit = slideOutVertically(spring()) { it / 10000 }
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) 0.3f else 0.7f),
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
                            Text(text = "No transactions")
                        }
                    }
                }

                val grouped = homeScreenUiState.transactions.sortedByDescending { it.date }
                    .groupBy { it.date }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    userScrollEnabled = true,
//                    userScrollEnabled = currentBottomNavDestination != HomeScreen.Home.route,
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    grouped.forEach { (date, transactions) ->
                        stickyHeader {
                            Header(
                                LocalDate.parse(date)
                                    .format(DateTimeFormatter.ofPattern("E, dd MMM yyyy"))
                            )
                        }

                        items(transactions) { transaction ->
                            Transaction(
                                transaction
                            ) { data: Transaction ->
                                onSelectTransaction(data)
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TransactionsListPreview() {
    CupidCustomerAppTheme {
        //TransactionsList(Transaction.transactions, {}, )
    }
}