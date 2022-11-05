package com.smartflowtech.cupidcustomerapp.ui.presentation.login

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    goToHomeScreen: () -> Unit,
    goToResetPassword: (String) -> Unit,
    finishActivity: () -> Unit,
    login: suspend (email: String, password: String) -> LoginState,
    username: String,
    userEmail: String,
    onboarded: Boolean
) {

    val scaffoldState = rememberScaffoldState()
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var notMe by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable(notMe) { mutableStateOf(if (notMe) "" else userEmail) }
    var password by rememberSaveable(notMe) { mutableStateOf("") }
    var isEmailError by rememberSaveable(notMe) { mutableStateOf(false) }
    var isPasswordError by rememberSaveable(notMe) { mutableStateOf(false) }
    var emailErrorLabel by rememberSaveable(notMe) { mutableStateOf("") }
    var passwordErrorLabel by rememberSaveable(notMe) { mutableStateOf("") }
    var showLoadingIndicator by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    fun validateEmail(trimmedEmail: String) {
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
    }

    fun validatePassword(password: String) {
        if (password.isEmpty()) {
            passwordErrorLabel = "Input valid password"
            isPasswordError = true
        } else {
            passwordErrorLabel = ""
            isPasswordError = false
        }
    }

    fun doLogin(email: String, password: String) {
        coroutineScope.launch {
            val loginState = login(email, password)
            when (loginState.viewModelResult) {
                ViewModelResult.ERROR -> {
                    showLoadingIndicator = false
                    if (loginState.message?.isNotEmpty() == true) {
                        if (scaffoldState.snackbarHostState
                                .currentSnackbarData == null
                        ) {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = loginState.message,
                                duration = SnackbarDuration.Short
                            )
                        }

                    }
                }
                ViewModelResult.SUCCESS -> {
                    goToHomeScreen()
                }
                else -> {}
            }
        }
    }

    BackHandler(onboarded) {
        finishActivity()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding(),
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    backgroundColor = transparentPink,
                    contentColor = red,
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
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                item {
                    Spacer(
                        modifier = Modifier
                            .height(48.dp)
                            .align(Alignment.TopStart)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart),
                        text = if (!notMe && username.isNotEmpty())
                            "Welcome Back, ${username}"
                        else
                            "Login to your account",
                        style = MaterialTheme.typography.h6,
                        color = darkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart),
                        text = "Please login to your account",
                        style = MaterialTheme.typography.body1,
                        color = grey
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    //Email
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = email,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        onValueChange = { text ->
                            email = text
                        },
                        singleLine = true,
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        label = {
                            if (isPasswordError) {
                                Text(text = passwordErrorLabel)
                            } else {
                                Text(text = "Password")
                            }
                        },
                        singleLine = true,
                        isError = isPasswordError,
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
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
                    if (showLoadingIndicator) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp, modifier = Modifier
                                .height(54.dp)
                        )
                    } else {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                            enabled = email.isNotEmpty() && password.isNotEmpty(),
                            onClick = {

                                val trimmedEmail = email.trim()
                                validateEmail(trimmedEmail)

                                validatePassword(password)

                                if (!isEmailError && !isPasswordError) {
                                    showLoadingIndicator = true
                                    doLogin(trimmedEmail, password)
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary
                            )
                        ) {
                            Text(text = "Login")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    //Forgot password
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(
                                    enabled = !showLoadingIndicator
                                ) {
                                    goToResetPassword("reset_password_screen")
                                }
                                .padding(8.dp),
                            text = "Forgot Password?",
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    //Remember me, Not me
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable(
                                enabled = !showLoadingIndicator
                            ) {
                                notMe = !notMe
                            }
                            .padding(8.dp),
                    ) {
                        if (notMe) {
                            Text(
                                text = "Remember me",
                                style = MaterialTheme.typography.body1
                            )
                        } else {
                            Text(
                                text = "Not me? ",
                                style = MaterialTheme.typography.body1
                            )
                            Text(
                                text = "Login",
                                style = MaterialTheme.typography.body1,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                item {
                    //Powered by Smartflow
                    Text(
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = "Powered by Smartflow Technologies",
                        style = MaterialTheme.typography.caption,
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CupidCustomerAppTheme {
        LoginScreen(
            goToHomeScreen = {},
            goToResetPassword = {},
            finishActivity = {},
            login = { _, _ -> LoginState((ViewModelResult.SUCCESS)) },
            username = "Hassan Abdulwahab",
            userEmail = "abc@gmail.com",
            onboarded = true
        )
    }
}