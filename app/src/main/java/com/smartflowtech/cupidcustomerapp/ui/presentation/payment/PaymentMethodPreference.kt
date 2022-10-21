package com.smartflowtech.cupidcustomerapp.ui.presentation.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.PaymentGateway
import com.smartflowtech.cupidcustomerapp.model.PaymentMethodPreference
import com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds.AddFundsCardPaymentGateway
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.lineGrey
import com.smartflowtech.cupidcustomerapp.ui.theme.transparentBlue
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeEachWord

@Composable
fun PaymentMethodPreference(
    paymentMethodPref: PaymentMethodPreference,
    onClick: (String: PaymentMethodPreference) -> Unit,
    isSelected: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    onClick(paymentMethodPref)
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
                    id = when (paymentMethodPref) {
                        PaymentMethodPreference.PAYSTACK -> R.drawable.ic_paystack
                        else -> R.drawable.ic_always_ask
                    }
                ),
                contentDescription = "Icon",
                tint = Color.Unspecified
            )

            Row(
                Modifier
                    .weight(2f, true)
                    .padding(start = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = paymentMethodPref.name.capitalizeEachWord(),
                    fontFamily = AthleticsFontFamily,
                    fontWeight = FontWeight.W400
                )

                RadioButton(
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(50))
                        .padding(8.dp)
                        .clipToBounds(),
                    selected = isSelected,
                    onClick = {
                        onClick(paymentMethodPref)
                    },
                    colors = RadioButtonDefaults.colors(selectedColor = darkBlue)
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
fun PreviewPaymentMethodSettings() {
    var selectedPaymentMethod by remember { mutableStateOf("") }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            listOf(
                PaymentMethodPreference.ASK_ALWAYS,
                PaymentMethodPreference.PAYSTACK
            )
        ) { item ->
            PaymentMethodPreference(
                paymentMethodPref = item,
                onClick = { paymentMethodPref ->
                    selectedPaymentMethod = paymentMethodPref.name
                    //update remote and local preference
                },
                isSelected = selectedPaymentMethod == item.name
            )
        }

    }
}