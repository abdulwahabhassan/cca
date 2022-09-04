package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.WalletCard
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AddFundsKeyPad(
    displayValue: String,
    onDisplayValueUpdated: (String) -> Unit,
    showSnackBar: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
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
                text = displayValue,
                maxLines = 1,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = AthleticsFontFamily,
                fontSize = 30.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp), text = "Select popular amount", color = faintGrey
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {

            listOf(
                "1000.00",
                "20000.00",
                "50.00",
                "1600.00",
                "3000.00",
                "150.00"
            ).forEach { amount ->
                item {
                    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                        Button(
                            onClick = {
                                val amt = if (displayValue.isNotEmpty()) {
                                    Util.formatAmount(
                                        displayValue.substringBefore(".")
                                            .replace(",", "")
                                            .toInt() + amount.substringBefore(".").toInt()
                                    ).substringBefore(".")
                                } else {
                                    Util.formatAmount(amount).substringBefore(".")
                                }

                                val value = amt.substringBefore(".").replace(",", "").toInt()
                                if (value < 1_000_000) {
                                    onDisplayValueUpdated(
                                        amt
                                    )
                                } else {
                                    showSnackBar("Maximum limit exceeded")
                                }

                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(
                                    1f, 1f, 1f, 0.1f
                                )
                            ),
                            shape = CircleShape,
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
                        )

                        {
                            Text(
                                text = "+₦${Util.formatAmount(amount).substringBefore(".")}",
                                color = Color.White,
                                fontFamily = AthleticsFontFamily,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

        }


        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            items(listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "<", "0", "x")) { item ->

                Button(
                    onClick = {
                        when (item) {
                            "<" -> {
                                val amount =
                                    if (displayValue.length != 1 && displayValue.isNotEmpty()) {
                                        Util.formatAmount(
                                            displayValue.replace(",", "")
                                                .dropLast(1)
                                        ).substringBefore(".")
                                    } else {
                                        ""
                                    }
                                onDisplayValueUpdated(amount)
                            }
                            "x" -> {
                                onDisplayValueUpdated("")
                            }
                            else -> {
                                val amount = Util.formatAmount(
                                    displayValue
                                        .replace(",", "") + item
                                ).substringBefore(".")
                                val value = amount.substringBefore(".").replace(",", "").toInt()
                                if(value < 1_000_000) {
                                    onDisplayValueUpdated(amount)
                                } else {
                                    showSnackBar("Maximum limit exceeded")
                                }
//                                if (displayValue.length < 7) {
//                                    val amount = Util.formatAmount(
//                                        displayValue
//                                            .replace(",", "") + item
//                                    ).substringBefore(".")
//                                    onDisplayValueUpdated(amount)
//                                } else {
//                                    showSnackBar("Maximum limit exceeded")
//                                }

                            }
                        }


                    },

                    modifier = Modifier
                        .height(LocalConfiguration.current.screenWidthDp.dp * 0.18f)
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(
                            1f, 1f, 1f, 0.1f
                        )
                    ),
                    shape = RoundedCornerShape(25),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
                )

                {
                    Text(
                        text = item,
                        color = Color.White,
                        fontFamily = AthleticsFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

@Composable
@Preview()
fun AddFundsPreview() {
    CupidCustomerAppTheme {
        AddFundsKeyPad("", {}, {})
    }
}