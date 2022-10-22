package com.smartflowtech.cupidcustomerapp.ui.presentation.password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey

@Composable
fun VerificationEmail() {

    LazyColumn(
        Modifier
            .fillMaxSize()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(16.dp),
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .height(48.dp)

            )
            //Change password
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Verification Email Sent",
                style = MaterialTheme.typography.h6,
                color = darkBlue
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "We sent an email to hass********.com \nPlease verify your email",
                style = MaterialTheme.typography.body1,
                color = grey
            )
            Spacer(modifier = Modifier.height(60.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_verification_email),
                contentDescription = "Icon",
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(60.dp))
            //Proceed
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                onClick = {
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text(text = "Open Email")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewVerificationEmail() {
    CupidCustomerAppTheme {
        VerificationEmail()
    }
}