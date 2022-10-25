package com.smartflowtech.cupidcustomerapp.ui.presentation.password

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.*

@Composable
fun VerifyEmailScreen(
    goToNewPasswordScreen: () -> Unit,
    goToResetPasswordScreen: () -> Unit,
) {

    BackHandler(true) {
        goToResetPasswordScreen()
    }

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    backgroundColor = transparentPink,
                    contentColor = darkBlue,
                    snackbarData = data
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.TopCenter
        ) {

            Image(
                painter = painterResource(id = R.drawable.design_background),
                contentDescription = "background"
            )

            Image(
                modifier = Modifier
                    .padding(top = LocalConfiguration.current.screenHeightDp.dp * 0.10f)
                    .size(60.dp)
                    .align(
                        Alignment.TopCenter
                    ),
                painter = painterResource(id = R.drawable.ic_smartflow),
                contentDescription = "Vendor logo"
            )

            LazyColumn(
                Modifier
                    .fillMaxHeight(0.68f)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(horizontal = 16.dp),
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
                            goToNewPasswordScreen()
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
    }


}

@Composable
@Preview(showBackground = true)
fun PreviewVerificationEmail() {
    CupidCustomerAppTheme {
        VerifyEmailScreen(goToNewPasswordScreen = {}, goToResetPasswordScreen = {})
    }
}