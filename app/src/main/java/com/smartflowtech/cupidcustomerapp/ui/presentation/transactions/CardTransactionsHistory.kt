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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.Period
import com.smartflowtech.cupidcustomerapp.model.domain.CardHistoryPeriodFilterContext
import com.smartflowtech.cupidcustomerapp.model.domain.Transaction
import com.smartflowtech.cupidcustomerapp.model.domain.Wallet
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.HomeScreenUiState
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.CardHistoryViewModel
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CardTransactionHistory(
    viewModel: CardHistoryViewModel = hiltViewModel(),
    homeScreenUiState: HomeScreenUiState,
    onSelectTransaction: (Transaction) -> Unit,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    selectedCardNfcTagCode: String,
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    currentBottomNavDestination: String,
    onGraphFilterClicked: (context: CardHistoryPeriodFilterContext, periods: List<String>) -> Unit,
    selectedMonthYearPeriod: String,
    cardHistoryPeriodFilterContext: CardHistoryPeriodFilterContext
) {

    val monthYearPeriodFilterList = remember {
        (0..11).map { currentValue ->
            LocalDate
                .now()
                .minusMonths(currentValue.toLong())
                .format(DateTimeFormatter.ofPattern("MMM yyyy"))
        }
    }

    val customPeriodFilterList = remember {
        listOf(
            Period.ONE_WEEK.name,
            Period.ONE_MONTH.name,
            Period.ONE_YEAR.name
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
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
                                color = if (selectedTab == "Analytics")
                                    darkBlue
                                else
                                    Color.Transparent,
                                shape = RoundedCornerShape(50)
                            ),
                        color = if (selectedTab == "Analytics") darkBlue else Color.Transparent
                    )
                }
            }

            if (selectedTab != "Transactions") {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clipToBounds()
                        .clickable {
                            onGraphFilterClicked(
                                CardHistoryPeriodFilterContext.MONTH_YEAR,
                                monthYearPeriodFilterList
                            )
                        }
                        .padding(horizontal = 8.dp)
                        .align(Alignment.Bottom),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = selectedMonthYearPeriod, fontSize = 12.sp)
                    Icon(
                        imageVector = Icons.Rounded.ExpandMore,
                        contentDescription = "Drop down icon"
                    )
                }

            }
        }

        Divider(thickness = 0.5.dp, color = lineGrey)

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedTab == "Transactions") {

            TransactionsList(
                homeScreenUiState = homeScreenUiState,
                onSelectTransaction = onSelectTransaction,
                selectedCardNfcTagCode = selectedCardNfcTagCode,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                currentBottomNavDestination = currentBottomNavDestination,
            )
        } else {

            val transactions =
                remember(selectedMonthYearPeriod, cardHistoryPeriodFilterContext) {
                    when (cardHistoryPeriodFilterContext) {
                        CardHistoryPeriodFilterContext.DEFAULT -> {
                            when (viewModel.appConfigPreferences.transactionPeriodFilter) {
                                Period.ONE_WEEK.name -> {
                                    homeScreenUiState.transactions
                                        .asSequence()
                                        .sortedBy { it.date }
                                        .filter {
                                            if (selectedCardNfcTagCode.isNotEmpty())
                                                it.nfcTagCode == selectedCardNfcTagCode else true
                                        }
                                        .filter { it.date != null }
                                        .filter {
                                            LocalDate.parse(it.date) >=
                                                    LocalDate.now().minusDays(7)
                                        }
                                        .groupBy { it.date?.substring(8) }
                                }
                                Period.ONE_MONTH.name -> {
                                    homeScreenUiState.transactions
                                        .asSequence()
                                        .sortedBy { it.date }
                                        .filter {
                                            if (selectedCardNfcTagCode.isNotEmpty())
                                                it.nfcTagCode == selectedCardNfcTagCode else true
                                        }
                                        .filter { it.date != null }
                                        .filter {
                                            LocalDate.parse(it.date) >=
                                                    LocalDate.now().minusDays(30)
                                        }
                                        .groupBy { it.date?.substring(8) }
                                }
                                else -> {
                                    //TWO YEAR
                                    homeScreenUiState.transactions
                                        .asSequence()
                                        .sortedBy { it.date }
                                        .filter {
                                            if (selectedCardNfcTagCode.isNotEmpty())
                                                it.nfcTagCode == selectedCardNfcTagCode else true
                                        }
                                        .filter { it.date != null }
                                        .filter {
                                            LocalDate.parse(it.date) >=
                                                    LocalDate.now().minusDays(720)
                                        }
                                        .groupBy { it.date?.substring(5, 7) }
                                }
                            }
                        }
                        CardHistoryPeriodFilterContext.MONTH_YEAR -> {
                            homeScreenUiState.transactions
                                .asSequence()
                                .sortedBy { it.date }
                                .filter {
                                    if (selectedCardNfcTagCode.isNotEmpty())
                                        it.nfcTagCode == selectedCardNfcTagCode else true
                                }
                                .filter { it.date != null }
                                .filter {
                                    val dateMonthYear = LocalDate.parse(it.date).format(
                                        DateTimeFormatter.ofPattern("MMM yyyy")
                                    )
                                    dateMonthYear == selectedMonthYearPeriod
                                }
                                .groupBy { it.date?.substring(8) }
                        }
                    }
                }

            val averageTransactions = remember(transactions) {
                transactions.mapValues { entry ->
                    val totalAmount = entry.value.filterNot { it.amount == null }
                        .sumOf { it.amount?.toDouble()!! }
                    totalAmount / entry.value.size
                }
            }

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
                            Text(text = "Credit", color = grey)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "₦0.00",
                                fontWeight = FontWeight.Bold
                            )
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
                            Text(text = "Debit", color = grey)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "₦${
                                Util.formatAmount(transactions.values.sumOf { list ->
                                    list.sumOf { it.amount?.toDouble() ?: 0.00 }
                                })
                            }", fontWeight = FontWeight.Bold)
                        }
                    }
                }

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
                                onGraphFilterClicked(
                                    CardHistoryPeriodFilterContext.DEFAULT,
                                    customPeriodFilterList
                                )
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

//                val averageTransactions = transactions.mapValues { entry ->
//                    val totalAmount = entry.value.filterNot { it.amount == null }
//                        .sumOf { it.amount?.toDouble()!! }
//                    totalAmount / entry.value.size
//                }

//                Timber.d("Transactions $transactions")
                Timber.d("Avg Transactions $averageTransactions")

                Box(
                    modifier = Modifier
                        .padding(start = 24.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
                        .fillMaxSize()
                        .background(Color.Transparent)
                ) {
                    if (averageTransactions.isEmpty()) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(50.dp),
                                painter = painterResource(id = R.drawable.ic_no_data),
                                contentDescription = "No analytics available icon",
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "No Analytics available to show", color = darkBlue)
                        }
                    } else {
                        val yStep = averageTransactions.values.sum() / averageTransactions.size
                        val points = averageTransactions.values.map { it.toFloat() }.toMutableList()
                        val xValues = averageTransactions.keys.map { it?.toInt()!! }.toMutableList()
                        val initialXValues = xValues.toList()
                        initialXValues.forEachIndexed { index, elem ->
                            if (index != 0 && index != initialXValues.lastIndex) {
                                val absDiff = abs(initialXValues[index + 1] - elem)
                                Timber.d("abs Diff -> $absDiff")
                                if (absDiff != 1 && absDiff < when (cardHistoryPeriodFilterContext) {
                                        CardHistoryPeriodFilterContext.DEFAULT -> {
                                            when (
                                                viewModel.appConfigPreferences.transactionPeriodFilter
                                            ) {
                                                Period.ONE_WEEK.name -> 7 - 1
                                                Period.ONE_MONTH.name -> 30 - 1
                                                else -> 12 - 1
                                            }
                                        }
                                        CardHistoryPeriodFilterContext.MONTH_YEAR -> {
                                            30 - 1
                                        }
                                    }
                                ) {
                                    xValues.addAll(
                                        index + 1,
                                        ((elem + 1)..absDiff).toList()
                                    )
                                    points.addAll(
                                        index + 1,
                                        ((elem + 1)..absDiff).toList().map { 0f }
                                    )
                                }

                            }
                        }

                        var nextValue: Int

                        do {
                            nextValue =
                                if (xValues.last() + 1 > when (cardHistoryPeriodFilterContext) {
                                        CardHistoryPeriodFilterContext.DEFAULT -> {
                                            when (
                                                viewModel.appConfigPreferences.transactionPeriodFilter
                                            ) {
                                                Period.ONE_WEEK.name -> 7
                                                Period.ONE_MONTH.name -> 30
                                                else -> 12
                                            }
                                        }
                                        CardHistoryPeriodFilterContext.MONTH_YEAR -> {
                                            30
                                        }
                                    }
                                ) 1 else xValues.last() + 1

                            if (nextValue != xValues.first()) {
                                xValues.add(nextValue)
                                points.add(0f)
                            }
                        } while (xValues.size != when (
                                viewModel.appConfigPreferences.transactionPeriodFilter
                            ) {
                                Period.ONE_WEEK.name -> 7
                                Period.ONE_MONTH.name -> 30
                                else -> 12
                            }
                        )

                        Timber.d("$xValues")
                        Timber.d("$points")
                        Graph(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            xValues = xValues,
                            yValues = (
                                    0..(averageTransactions.values.max() /
                                            averageTransactions.values.min()).toInt()
                                    ).map { (it + 1) * yStep.toInt() },
                            points = points,
                            paddingSpace = 16.dp,
                            verticalStep = yStep.toInt(),
                            periodFilter = viewModel.appConfigPreferences.transactionPeriodFilter,
                            cardHistoryPeriodFilterContext = cardHistoryPeriodFilterContext
                        )
                    }
                }
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
                        "VLX-5324",
                        "2022-01-01 12:00:00"
                    )
                ),
                message = null,
                wallets = listOf(
                    Wallet(
                        "Smartflow",
                        1L,
                        "123000.00",
                        "2022-19-05",
                        "450000.00",
                        39930,
                        "VLX-5324"

                    )
                )
            ),
            bottomSheetScaffoldState = BottomSheetScaffoldState(
                DrawerState(DrawerValue.Closed), BottomSheetState(BottomSheetValue.Collapsed),
                SnackbarHostState()
            ),
            onSelectTransaction = {},
            selectedCardNfcTagCode = "VLX-E8393",
            selectedTab = "Analytics",
            onTabSelected = {},
            currentBottomNavDestination = "",
            onGraphFilterClicked = { _, _ -> },
            selectedMonthYearPeriod = "May 2022",
            cardHistoryPeriodFilterContext = CardHistoryPeriodFilterContext.DEFAULT
        )
    }
}