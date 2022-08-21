package com.smartflowtech.cupidcustomerapp.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme

@Composable
fun GetStartedFirstScreen(goToGetStartedSecondScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.Start
    ) {
        Column(Modifier.padding(horizontal = 16.dp)) {
            Text(
                text = "Payments made easy",
                color = Color.White,
                style = MaterialTheme.typography.h6
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enjoy seamless payments with all from a single mobile app",
                color = Color.White,
                style = MaterialTheme.typography.body1,
            )
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                onClick = { goToGetStartedSecondScreen() },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(text = "Get Started")
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Spacer(
            Modifier
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF1B6A)
@Composable
fun GetStartedFirstScreenPreview() {
    CupidCustomerAppTheme {
        GetStartedFirstScreen({})
    }
}