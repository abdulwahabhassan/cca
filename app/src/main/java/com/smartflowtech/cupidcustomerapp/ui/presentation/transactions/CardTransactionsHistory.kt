package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.model.Wallet
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardTransactionHistory(
    homeScreenUiState: HomeScreenUiState,
    onSelectTransaction: (Transaction) -> Unit,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    currentBottomNavDestination: String
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth(unbounded = true)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(3.dp))
                    .clip(RoundedCornerShape(3.dp))
                    .clipToBounds()
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) {
                        onTabSelected("Transactions")
                    }
                    .padding(top = 4.dp, start = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Transactions",
                    color = Color.Black,
                    fontFamily = AthleticsFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .width(50.dp)
                        .height(2.dp)
                        .background(
                            color = if (selectedTab == "Transactions") darkBlue else Color.Transparent,
                            shape = RoundedCornerShape(50)
                        ),
                    color = if (selectedTab == "Transactions") darkBlue else Color.Transparent
                )
            }

            Spacer(modifier = Modifier.width(32.dp))

            Column(
                modifier = Modifier
                    .wrapContentWidth(unbounded = true)
                    .background(color = Color.Transparent, shape = RoundedCornerShape(3.dp))
                    .clip(RoundedCornerShape(3.dp))
                    .clipToBounds()
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) {
                        onTabSelected("Analytics")
                    }
                    .padding(top = 4.dp, start = 8.dp, end = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Analytics",
                    color = Color.Black,
                    fontFamily = AthleticsFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(4.dp))
                Divider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .width(50.dp)
                        .height(2.dp)
                        .background(
                            color = if (selectedTab == "Analytics") darkBlue else Color.Transparent,
                            shape = RoundedCornerShape(50)
                        ),
                    color = if (selectedTab == "Analytics") darkBlue else Color.Transparent
                )
            }
        }

        Divider(thickness = 0.5.dp, color = lineGrey)

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedTab == "Transactions") {
            TransactionsList(
                homeScreenUiState = homeScreenUiState,
                onSelectTransaction = onSelectTransaction,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                getTransactions = {},
                currentBottomNavDestination = currentBottomNavDestination
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Data is currently unavailable",
                    color = purple,
                    modifier = Modifier
                        .background(
                            color = transparentPurple,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun CardTransactionHistoryPreview() {
    CupidCustomerAppTheme {
        CardTransactionHistory(
            homeScreenUiState = HomeScreenUiState(
                viewModelResult = ViewModelResult.SUCCESS,
                transactions = listOf(
                    Transaction(
                        "Completed",
                        "11:00:02",
                        "Transaction",
                        "45000.00",
                        "2022-09-23",
                        "Key Tag",
                        "26716727",
                        "NNPC",
                        "PMS",
                    )
                ),
                message = null,
                wallets = listOf(
                    Wallet(
                        "Smartflow",
                        1L,
                        "123000.00",
                        "2022-19-05",
                        "450000.00"
                    )
                )
            ),
            bottomSheetScaffoldState = BottomSheetScaffoldState(
                DrawerState(DrawerValue.Closed), BottomSheetState(BottomSheetValue.Collapsed),
                SnackbarHostState()
            ),
            onSelectTransaction = {},
            selectedTab = "Transactions",
            onTabSelected = {},
            currentBottomNavDestination = ""
        )
    }
}