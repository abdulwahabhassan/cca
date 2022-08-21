package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
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
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Home(goToTransactions: () -> Unit) {
    val pagerState = rememberPagerState()
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.Start
    ) {
        HorizontalPager(
            count = 2,
            state = pagerState,
            contentPadding = PaddingValues(start = 20.dp, end = 60.dp),
            itemSpacing = 20.dp,
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
                Icon(imageVector = Icons.Rounded.ArrowForward, contentDescription = "Forward arrow")
            }
        }

        Transactions()
    }
}

@Composable
@Preview(showBackground = true)
fun HomePreview() {
    Home {

    }
}

