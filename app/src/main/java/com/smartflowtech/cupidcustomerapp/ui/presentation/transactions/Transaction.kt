package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.foundation.background
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
import com.smartflowtech.cupidcustomerapp.ui.theme.*

@Composable
fun Transaction(status: String, time: String, title: String, amount: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {}) {
                Icon(
                    modifier = Modifier
                        .background(transparentBlue, shape = RoundedCornerShape(50))
                        .padding(8.dp),
                    painter = painterResource(id = R.drawable.ic_arrow_diagonal),
                    contentDescription = "Diagonal arrow",
                    tint = blue
                )
            }

            Column(
                Modifier
                    .weight(2f, true)
                    .padding(start = 16.dp)
            ) {
                Text(text = title, fontFamily = AthleticsFontFamily, fontWeight = FontWeight.W400)
                Row {
                    Text(
                        text = status,
                        modifier = Modifier.padding(end = 4.dp),
                        fontSize = 12.sp,
                        color = grey,
                        fontFamily = AthleticsFontFamily
                    )
                    Text(text = time, fontSize = 12.sp, color = grey)
                }
            }
            Text(
                amount,
                fontWeight = FontWeight.SemiBold,
                fontFamily = AthleticsFontFamily,
                modifier = Modifier.padding(end = 6.dp)
            )

        }
        Divider(
            color = lineGrey,
            thickness = 0.5.dp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun TransactionPreview() {
    Transaction("Completed", "11:24PM", "Transaction", "₦167,000.00")
}
