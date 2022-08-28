package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.WalletCard
import com.smartflowtech.cupidcustomerapp.ui.theme.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddFundsKeyPad() {

    val amount by rememberSaveable { mutableStateOf("4,000") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            color = Color.White,
            fontFamily = AthleticsFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold, text = "Add Funds"
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp), text = "Enter amount", color = faintGrey
        )

        Row(
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .background(
                        color = Color(
                            1f, 1f, 1f, 0.1f
                        ), shape = RoundedCornerShape(45)
                    )
                    .padding(12.dp),
                text = "₦", color = Color.White, fontFamily = AthleticsFontFamily,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
                text = amount,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = AthleticsFontFamily,
                fontSize = 30.sp,
            )
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp), text = "Select popular amount", color = faintGrey
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
        ) {

            listOf("₦1,000.00", "₦20,000.00", "₦50.00", "₦1,600.00").forEach { amount ->
                item {
                    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                        Text(
                            modifier = Modifier
                                .background(
                                    color = Color(
                                        1f, 1f, 1f, 0.1f
                                    ), shape = RoundedCornerShape(25)
                                )
                                .padding(horizontal = 12.dp, vertical = 12.dp),
                            text = amount, color = Color.White, fontFamily = AthleticsFontFamily,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }

        }
    }

}

@Composable
@Preview()
fun AddFundsPreview() {
    CupidCustomerAppTheme {
        AddFundsKeyPad()
    }
}