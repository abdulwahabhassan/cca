package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.theme.lineGrey

@Composable
fun AddFundsUssdPaymentMode(code: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Dial the code below on your mobile phone to complete the payment",
            color = darkBlue,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = code,
            fontWeight = FontWeight.Bold,
            color = grey,
            fontSize = 22.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tap here to copy code",
                color = grey
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
        Spacer(modifier = Modifier.height(64.dp))
        Text(
            text = "Choose another bank",
            color = darkBlue,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .clickable {}
                .padding(8.dp)
        )

    }

}

@Composable
@Preview(showBackground = true)
fun AddFundsUssdPaymentModePreview() {
    CupidCustomerAppTheme {
        AddFundsUssdPaymentMode("*234*000*0320#")
    }
}