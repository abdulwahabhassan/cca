package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.PaymentMode
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.theme.lineGrey
import com.smartflowtech.cupidcustomerapp.ui.theme.transparentBlue
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeEachWord

@Composable
fun AddFundsPaymentMode(data: PaymentMode, onClick: (paymentMode: PaymentMode) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
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
                    .background(transparentBlue, shape = CircleShape)
                    .size(36.dp)
                    .padding(8.dp),
                painter = painterResource(
                    id = when (data) {
                        PaymentMode.BANK_TRANSFER -> R.drawable.ic_bank
                        PaymentMode.USSD -> R.drawable.ic_ussd
                        PaymentMode.CARD  -> R.drawable.ic_card
                    }
                ),
                contentDescription = "PaymentSettings mode icon",
                tint = Color.Unspecified
            )

            Column(
                Modifier
                    .weight(2f, true)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = data.name.capitalizeEachWord() ?: "",
                    fontFamily = AthleticsFontFamily,
                    fontWeight = FontWeight.W400
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (data) {
                        PaymentMode.BANK_TRANSFER -> "Top up wallet from your bank"
                        PaymentMode.USSD -> "Top up wallet using USSD"
                        PaymentMode.CARD  -> "Top up wallet from your card"
                    },
                    modifier = Modifier.padding(end = 4.dp),
                    fontSize = 12.sp,
                    color = grey,
                    fontFamily = AthleticsFontFamily
                )
            }

        }
        Divider(
            color = lineGrey,
            thickness = 0.5.dp,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun TransactionPreview() {
    AddFundsPaymentMode(
        PaymentMode.BANK_TRANSFER,
        { }
    )
}