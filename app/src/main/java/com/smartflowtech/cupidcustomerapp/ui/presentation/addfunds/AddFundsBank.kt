package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.Bank
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.grey

@Composable
fun AddFundsBank(bank: Bank, onClick: (Bank) -> Unit) {
    Row(
        modifier = Modifier
            .clickable {
                onClick(bank)
            }
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp),

        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(id = bank.icon),
            contentDescription = "Payment mode icon",
            tint = Color.Unspecified
        )
        Text(
            text = bank.name,
            modifier = Modifier
                .weight(2f, true)
                .padding(start = 16.dp),
            color = grey,
            fontFamily = AthleticsFontFamily
        )
    }
}

@Composable
@Preview(showBackground = true)
fun AddFundsBankPreview() {
    CupidCustomerAppTheme {
        AddFundsBank(Bank("First Bank of Nigeria", R.drawable.ic_firstbank), {})
    }
}