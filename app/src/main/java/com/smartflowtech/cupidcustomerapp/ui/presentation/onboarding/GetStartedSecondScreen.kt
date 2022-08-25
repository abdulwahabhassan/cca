package com.smartflowtech.cupidcustomerapp.ui.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.gradientWhiteBlue

@Composable
fun GetStartedSecondScreen(goToLoginScreen: () -> Unit, onGetStartedClicked: () -> Unit) {

    Box(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.img_person_5),
            contentDescription = "background",
            contentScale = ContentScale.FillWidth
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        gradientWhiteBlue,
                        endY = LocalConfiguration.current.screenHeightDp.toFloat() / 0.45f
                    )
                )
        )

        Column(
            Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = "Second start screen",
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
                onClick = {
                    onGetStartedClicked()
                    goToLoginScreen()
                          },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(text = "Get Started")
            }
            Spacer(modifier = Modifier.height(40.dp))
//            Spacer(
//                Modifier
//                    .windowInsetsBottomHeight(WindowInsets.navigationBars)
//                    .fillMaxWidth()
//            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, backgroundColor = 0xFF1B6A)
@Composable
fun GetStartedSecondScreenPreview() {
    CupidCustomerAppTheme {
        GetStartedSecondScreen({}, {})
    }
}