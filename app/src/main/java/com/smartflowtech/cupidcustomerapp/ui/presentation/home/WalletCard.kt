package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.GradientButton
import com.smartflowtech.cupidcustomerapp.ui.theme.brightBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.gradientBluePurple
import com.smartflowtech.cupidcustomerapp.ui.theme.lineGrey
import com.smartflowtech.cupidcustomerapp.ui.theme.skyBlue

@Composable
fun WalletCard() {
    Card(
        modifier = Modifier.fillMaxWidth(0.88f),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = skyBlue
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_card_decoration),
                    contentDescription = "Card decoration",
                    tint = Color.Unspecified
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 28.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row {
                            Text(
                                text = "Wallet Balance",
                                color = Color.Black,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Outlined.VisibilityOff,
                                contentDescription = "Balance Visibility"
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "₦0.00",
                            color = Color.Black,
                            style = MaterialTheme.typography.h6
                        )
                    }
                    GradientButton(
                        text = "+Add funds",
                        gradient = Brush.horizontalGradient(
                            gradientBluePurple
                        ),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Divider(
                    thickness = 1.dp,
                    color = lineGrey,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Card Number VLX-90345", fontSize = 12.sp)
                    Text(
                        text = "Loyalty Points: 406",
                        fontSize = 12.sp,
                        color = brightBlue
                    )
                }

            }
        }
    }
}