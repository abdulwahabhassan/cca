package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.model.Wallet
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import kotlin.random.Random

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

            Spacer(modifier = Modifier.width(24.dp))

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
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, true)
                            .padding(4.dp),
                        elevation = 0.5.dp,
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = cardYellow
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_credit),
                                contentDescription = "Credit icon",
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Debit", color = grey)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "₦123,000.00", fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, true)
                            .padding(4.dp),
                        elevation = 0.5.dp,
                        shape = RoundedCornerShape(16.dp),
                        backgroundColor = cardBlue
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_debit),
                                contentDescription = "Debit icon",
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Credit", color = grey)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "₦500,000.00", fontWeight = FontWeight.Bold)
                        }
                    }
                }

                val yStep = 50
                val points = listOf(150f, 100f, 250f, 200f, 330f, 300f, 90f, 120f, 285f, 199f)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Graph",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clipToBounds()
                            .clickable {

                            }
                            .padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Filter", fontSize = 12.sp)
                        Icon(
                            imageVector = Icons.Rounded.ExpandMore,
                            contentDescription = "Drop down icon"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(
                            LocalConfiguration.current.screenWidthDp.dp * 0.70f
                        )
                ) {

                    AnalyticsGraph(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp, end = 4.dp),
                        xValues = (0..9).map { it + 1 },
                        yValues = (0..6).map { (it + 1) * yStep },
                        points = points,
                        paddingSpace = 16.dp,
                        verticalStep = yStep
                    )
                }

                Text(
                    "Monthly Transactions",
                    color = purple,
                    modifier = Modifier
                        .background(
                            color = transparentPurple,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp
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
            selectedTab = "Analytics",
            onTabSelected = {},
            currentBottomNavDestination = ""
        )
    }
}