package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.TransactionsList
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.black

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun Home(
    goToTransactions: () -> Unit,
    onBackPressed: () -> Unit,
    transactions: List<Transaction>,
    bottomSheetState: BottomSheetState
) {
    val pagerState = rememberPagerState()

    BackHandler {
        onBackPressed()
    }

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
                    tint = black
                )
            }
        }

        TransactionsList(
            transactions = transactions,
            onTransactionClicked = { goToTransactions() },
            bottomSheetState = bottomSheetState
        )
    }
}

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    //Home ({})
}

