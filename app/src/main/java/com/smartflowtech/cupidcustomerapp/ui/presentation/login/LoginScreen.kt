package com.smartflowtech.cupidcustomerapp.ui.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.data.repo.LoginRepository
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.model.request.LoginRequestBody
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.LoginViewModel
import com.smartflowtech.cupidcustomerapp.ui.theme.*

@Composable
fun LoginScreen(
    onLoginClicked: (LoginRequestBody) -> Unit,
    uiState: LoginScreenUiState,
    goToHomeScreen: () -> Unit,
    goToForgotPasswordScreen: () -> Unit
) {

    val scaffoldState = rememberScaffoldState()
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("support@e360africa.com") }
    var password by rememberSaveable { mutableStateOf("P@ssw0rdl0g1n%%") }
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }
    var emailErrorLabel by rememberSaveable { mutableStateOf("") }
    var passwordErrorLabel by rememberSaveable { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.navigationBarsPadding().imePadding(),
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

            when (uiState.viewModelResult) {
                ViewModelResult.SUCCESS -> {
                    goToHomeScreen()
                }
                else -> {}
            }

            LaunchedEffect(uiState.viewModelResult) {
                if (!uiState.message.isNullOrEmpty()) {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = uiState.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.design_background),
                contentDescription = "background"
            )
            Column(
                Modifier
                    .fillMaxHeight(0.7f)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )
                    .padding(horizontal = 16.dp)
                    .align(Alignment.BottomCenter),
            ) {
                Spacer(modifier = Modifier.height(48.dp))
                Text(text = "Login to your account", style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Please login to your account", style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(32.dp))

                //Email
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { text ->
                        email = text
                    },
                    label = {
                        if (isEmailError) {
                            Text(text = emailErrorLabel)
                        } else {
                            Text(text = "Email")
                        }
                    },
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
                Spacer(modifier = Modifier.height(16.dp))

                //Password
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password,
                    onValueChange = { text ->
                        password = text
                    },
                    label = {
                        if (isPasswordError) {
                            Text(text = passwordErrorLabel)
                        } else {
                            Text(text = "Password")
                        }
                    },
                    isError = isPasswordError,
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description =
                            if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = {
                            passwordVisible =
                                !passwordVisible
                        }) {
                            Icon(imageVector = image, description, tint = Color.LightGray)
                        }
                    },
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

                //Login
                when (uiState.viewModelResult) {
                    ViewModelResult.LOADING, ViewModelResult.SUCCESS -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .height(54.dp),
                            color = darkBlue
                        )
                    }
                    else -> {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            enabled = email.isNotEmpty() && password.isNotEmpty(),
                            onClick = {
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
                                }

                                if (password.isEmpty()) {
                                    passwordErrorLabel =
                                        "Input valid password"
                                    isPasswordError = true
                                } else {
                                    passwordErrorLabel = ""
                                    isPasswordError = false
                                }

                                if (!isEmailError &&
                                    !isPasswordError
                                ) {
                                    onLoginClicked(LoginRequestBody(trimmedEmail, password))
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                        ) {
                            Text(text = "Login")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                //Forgot password
                Text(text = "Forgot password?")
                Spacer(modifier = Modifier.height(32.dp))

                //Not me, Login
//                Row(Modifier.padding(bottom = 24.dp)) {
//                    Text(text = "Not me?")
//                    Spacer(modifier = Modifier.width(4.dp))
//                    Text(text = "Login")
//                }

            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CupidCustomerAppTheme {
        //LoginScreen({  }, {}, {}, { _ -> })
    }
}