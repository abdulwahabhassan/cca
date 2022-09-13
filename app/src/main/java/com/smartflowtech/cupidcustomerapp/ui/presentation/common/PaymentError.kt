package com.smartflowtech.cupidcustomerapp.ui.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey

@Composable
fun PaymentError(message: String, info: String, onCloseDialog: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 32.dp),
    ) {

        Icon(
            modifier = Modifier
                .align(Alignment.End)
                .clip(CircleShape)
                .clickable {
                    onCloseDialog()
                }
                .padding(8.dp),
            imageVector = Icons.Rounded.Close,
            contentDescription = "Close icon"
        )

        Row(modifier = Modifier.fillMaxWidth()) {

            Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(id = R.drawable.ic_payment_error_icon),
                contentDescription = "Success icon",
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = message, fontWeight = FontWeight.Bold, color = darkBlue)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = info, color = grey)
            }
        }

    }

}

@Composable
@Preview(showBackground = true)
fun PaymentErrorPreview() {
    CupidCustomerAppTheme {
        PaymentError("Transaction Failed", "Insufficient balance", {})
    }
}