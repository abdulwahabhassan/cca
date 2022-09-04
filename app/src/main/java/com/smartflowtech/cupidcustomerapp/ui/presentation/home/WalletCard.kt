package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import android.service.quickaccesswallet.WalletCard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.GradientButton
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@Composable
fun WalletCard(
    backgroundColor: Color,
    onAddFundsClicked: () -> Unit,
    walletBalanceVisibility: Boolean,
    updateWalletVisibility: (Boolean) -> Unit,
    vendorName: String,
    currentBalance: String,
    onCardSelected: (String) -> Unit,
    isCardSelected: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onCardSelected(currentBalance)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = if (isCardSelected) 16.dp else 4.dp,
        backgroundColor = remember { backgroundColor }
    ) {

        Column {
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
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Wallet Balance",
                                color = Color.Black,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            IconButton(
                                onClick = {
                                    updateWalletVisibility(!walletBalanceVisibility)
                                }) {
                                Icon(
                                    imageVector = if (walletBalanceVisibility) Icons.Outlined.VisibilityOff
                                    else Icons.Outlined.Visibility,
                                    contentDescription = "Balance Visibility",
                                )
                            }
                        }

                        Text(
                            text = if (walletBalanceVisibility) "₦${
                                Util.formatAmount(
                                    currentBalance
                                )
                            }" else "******",
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
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        onClick = onAddFundsClicked
                    )
                }
                Divider(
                    thickness = 1.dp,
                    color = lineGrey,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Vendor: ${vendorName}", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Loyalty Points: 0",
                        fontSize = 12.sp,
                        color = brightBlue,
                    )
                }

            }
        }
    }
}

@Composable
@Preview(heightDp = 200)
fun WalletCardPreview() {
    CupidCustomerAppTheme {
        WalletCard(backgroundColor = transparentBlue,
            {},
            true,
            {},
            "Smartflow",
            "₦30,000",
            {},
            false
        )
    }
}