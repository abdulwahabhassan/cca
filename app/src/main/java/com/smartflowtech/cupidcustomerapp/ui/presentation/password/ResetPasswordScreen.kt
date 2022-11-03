package com.smartflowtech.cupidcustomerapp.ui.presentation.password

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.ResetPasswordViewModel
import com.smartflowtech.cupidcustomerapp.ui.theme.*

@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel,
    uiState: ResetPasswordScreenUiState,
    onBackArrowPressed: () -> Unit,
    goToVerifyEmailScreen: (String) -> Unit,
) {

    val scaffoldState = rememberScaffoldState()

    var email by rememberSaveable { mutableStateOf("") }

    // Error and labels
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var emailErrorLabel by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    backgroundColor = when (uiState.viewModelResult) {
                        ViewModelResult.SUCCESS -> transparentGreen
                        ViewModelResult.ERROR -> transparentPink
                        else -> transparentPurple
                    },
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

            LaunchedEffect(uiState.viewModelResult) {
                if (!uiState.message.isNullOrEmpty()) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiState.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }

            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
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

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 24.dp),
                onClick = {
                    onBackArrowPressed()
                }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back arrow",
                    tint = Color.White,
                )
            }

            LazyColumn(
                Modifier
                    .fillMaxHeight(0.68f)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(horizontal = 16.dp)
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
                        text = "Reset Password",
                        style = MaterialTheme.typography.h6,
                        color = darkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Please enter the email associated with your account",
                        style = MaterialTheme.typography.body1,
                        color = grey
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    //Enter email
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = email,
                        onValueChange = { text ->
                            email = text
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        label = {
                            if (isEmailError) {
                                Text(text = emailErrorLabel)
                            } else {
                                Text(text = "Enter email")
                            }
                        },
                        singleLine = true,
                        isError = isEmailError,
                        shape = RoundedCornerShape(10.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = lightGrey,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent
                        )
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    when (uiState.viewModelResult) {
                        ViewModelResult.INITIAL, ViewModelResult.ERROR -> {
                            //Proceed
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp),
                                enabled = email.isNotEmpty(),
                                onClick = {
                                    emailErrorLabel = ""
                                    isEmailError = false

                                    val trimmedEmail = email.trim()

                                    //Basic validator
                                    if (trimmedEmail.isEmpty() ||
                                        !trimmedEmail.contains("@") ||
                                        !trimmedEmail.contains(".")
                                    ) {
                                        emailErrorLabel = "Input valid email"
                                        isEmailError = true
                                    } else {
                                        emailErrorLabel = ""
                                        isEmailError = false
                                        viewModel.forgotPasswordVerifyEmail(email = trimmedEmail)
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.primary
                                )
                            ) {
                                Text(text = "Proceed")
                            }
                        }
                        ViewModelResult.LOADING, ViewModelResult.SUCCESS -> {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.height(54.dp)
                            )
                            if (uiState.viewModelResult == ViewModelResult.SUCCESS) {
                                goToVerifyEmailScreen(email)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

        }
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewResetPassword() {
    CupidCustomerAppTheme {
        //ResetPasswordScreen()
    }
}