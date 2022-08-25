package com.smartflowtech.cupidcustomerapp.ui.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onboarded: Boolean,
    loggedIn: Boolean,
    goToHomeScreen: () -> Unit,
    goToGetStartedScreen: () -> Unit,
    goToLoginScreen: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Powered by", color = Color.White)
        Image(
            painterResource(R.drawable.ic_smartflow_logo),
            contentDescription = "",
            modifier = Modifier
                .fillMaxHeight(0.1f)
                .fillMaxWidth(0.4f)
        )
        Spacer(modifier = Modifier.height(32.dp))

        LaunchedEffect(key1 = onboarded) {
            delay(1000)
            if (onboarded) {
                if (loggedIn) {
                    goToHomeScreen()
                } else {
                    goToLoginScreen()
                }
            } else {
                goToGetStartedScreen()
            }
        }
        Spacer(
            Modifier
                .windowInsetsBottomHeight(WindowInsets.navigationBars)
                .fillMaxWidth()
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF1B6A)
@Composable
fun SplashScreenPreview() {
    CupidCustomerAppTheme {
//        SplashScreen(false, {}, {}, {})
    }
}