package com.smartflowtech.cupidcustomerapp.ui.presentation.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.gradientWhiteBlue

@Composable
fun GetStartedFirstScreen(goToGetStartedSecondScreen: () -> Unit) {
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize()
            .background(color = darkBlue),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        var enabled by remember { mutableStateOf(true) }

        Column(Modifier.padding(top = 40.dp)) {
            Row(
                modifier = Modifier
                    .height(if (LocalConfiguration.current.screenHeightDp.dp > 700.dp) 200.dp else 130.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.Bottom
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight(0.75f)
                        .clip(RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp)),
                    painter = painterResource(id = R.drawable.img_person_3),
                    contentDescription = "image"
                )
                Spacer(modifier = Modifier.width(24.dp))
                Image(
                    modifier = Modifier
                        .padding(end = 24.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    painter = painterResource(id = R.drawable.img_person_2),
                    contentDescription = "image",
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .height(if (LocalConfiguration.current.screenHeightDp.dp > 700.dp) 200.dp else 130.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top,
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxHeight(0.75f)
                        .padding(start = 24.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    painter = painterResource(id = R.drawable.img_person_1),
                    contentDescription = "image"
                )
                Spacer(modifier = Modifier.width(24.dp))
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp)),
                    painter = painterResource(id = R.drawable.img_person_4),
                    contentDescription = "image"
                )
            }
        }


        Column(
            Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
        ) {

            Row {
                Icon(
                    painter = painterResource(id = R.drawable.ic_dash),
                    contentDescription = "Dash",
                    tint = Color.Unspecified
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_dot),
                    contentDescription = "Dot",
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

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
                enabled = enabled,
                onClick = {
                    enabled = false
                    goToGetStartedSecondScreen()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(text = "Get Started")
            }
            Spacer(modifier = Modifier.height(40.dp))

        }
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    backgroundColor = 0xFF1B6A
)
@Composable
fun GetStartedFirstScreenPreview() {
    CupidCustomerAppTheme {
        GetStartedFirstScreen({})
    }
}