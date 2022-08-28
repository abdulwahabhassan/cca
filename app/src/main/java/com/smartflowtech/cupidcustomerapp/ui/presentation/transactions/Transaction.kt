package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.theme.*

@Composable
fun Transaction(data: Transaction, onClick: (transaction: Transaction) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick(data)
                }
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                modifier = Modifier
                    .background(
                        when (data.status) {
                            "Completed" -> transparentBlue
                            "Pending" -> transparentYellow
                            "Failed" -> transparentPink
                            else -> transparentAsh
                        }, shape = RoundedCornerShape(50)
                    )
                    .padding(8.dp),
                painter = painterResource(
                    id = when (data.type) {
                        "Credit" -> R.drawable.ic_transaction_credit
                        "Debit" -> R.drawable.ic_transaction_debit
                        else -> R.drawable.ic_wallet
                    }
                ),
                contentDescription = "Diagonal arrow",
                tint = when (data.status) {
                    "Completed" -> blue
                    "Pending" -> yellow
                    "Failed" -> red
                    else -> black
                }
            )

            Column(
                Modifier
                    .weight(2f, true)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = data.title,
                    fontFamily = AthleticsFontFamily,
                    fontWeight = FontWeight.W400
                )
                Row {
                    Text(
                        text = data.status,
                        modifier = Modifier.padding(end = 4.dp),
                        fontSize = 12.sp,
                        color = grey,
                        fontFamily = AthleticsFontFamily
                    )
                    Text(text = data.time, fontSize = 12.sp, color = grey)
                }
            }
            Text(
                data.amount,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                fontFamily = AthleticsFontFamily,
                modifier = Modifier.padding(end = 6.dp)
            )

        }
        Divider(
            color = lineGrey,
            thickness = 0.5.dp,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun TransactionPreview() {
    Transaction(
        "Completed",
        "11:24PM",
        "Transaction",
        "₦167,000.00",
        "",
        "Credit",
        "TRS283883920",
        "Wallet Top-Up"
    )
}
