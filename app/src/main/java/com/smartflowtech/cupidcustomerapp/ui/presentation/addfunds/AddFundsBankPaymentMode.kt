package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.*

@Composable
fun AddFundsBankPaymentMode(accountNumber: String, bankName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = accountNumber,
                fontWeight = FontWeight.ExtraBold,
                color = darkBlue,
                fontSize = 22.sp
            )
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {

                    }
                    .padding(8.dp),
                painter = painterResource(
                    id = R.drawable.ic_copy
                ),
                contentDescription = "Copy icon"
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider(thickness = 1.dp, color = lineGrey)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = bankName, fontWeight = FontWeight.Bold, color = grey)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Money sent to this account number will automatically top up your Cupid",
            color = grey
        )
    }

}

@Composable
@Preview(showBackground = true)
fun AddFundsBankPaymentModePreview() {
    CupidCustomerAppTheme {
        AddFundsBankPaymentMode("35647729920", "Wema Bank")
    }
}