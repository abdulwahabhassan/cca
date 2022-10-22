package com.smartflowtech.cupidcustomerapp.ui.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey

@Composable
fun Success(
    title: String,
    message: String,
    buttonText: String = "Okay",
    onOkayPressed: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.ic_success),
            contentDescription = "Success icon",
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.height(8.dp))
        //Message text
        Text(text = title, color = darkBlue, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))
        //Info text
        Text(text = message, color = grey, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(32.dp))
        //Okay button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            onClick = {
                onOkayPressed()
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(text = buttonText)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PaymentSuccessPreview() {
    CupidCustomerAppTheme {
        Success(
            title = "Sent",
            message = "We've sent the requested statements to your email",
            onOkayPressed = {})
    }
}