package com.smartflowtech.cupidcustomerapp.ui.presentation.password

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.Success
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.NewPasswordViewModel
import com.smartflowtech.cupidcustomerapp.ui.theme.*


@Composable
fun NewPasswordScreen(
    viewModel: NewPasswordViewModel,
    uiState: NewPasswordScreenUiState,
    goToLoginScreen: () -> Unit,
    goToResetPasswordScreen: () -> Unit
) {

    BackHandler(true) {
        goToResetPasswordScreen()
    }

    // Visibility and input text
    var newPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var newPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    // Error and labels
    var isNewPasswordError by rememberSaveable { mutableStateOf(false) }
    var isConfirmPasswordError by rememberSaveable { mutableStateOf(false) }
    var newPasswordErrorLabel by rememberSaveable { mutableStateOf("") }
    var confirmPasswordErrorLabel by rememberSaveable { mutableStateOf("") }

    // Password validation
    val isEightCharactersLong by rememberSaveable { derivedStateOf { newPassword.length >= 8 } }
    val hasAtLeastOneUpperCaseLetter by rememberSaveable {
        derivedStateOf { newPassword.contains(Regex("[A-Z]")) }
    }
    val hasAtLeastOneSpecialCharacter by rememberSaveable {
        derivedStateOf { newPassword.contains(Regex("!|@|#|\\$|%|\\^|&|\\*")) }
    }
    val hasAtLeastOneLowerCaseLetter by rememberSaveable {
        derivedStateOf { newPassword.contains("a-z") }
    }

    var showSuccessDialog by rememberSaveable { mutableStateOf(false) }

    val scaffoldState = rememberScaffoldState()

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
                        text = "Change Password",
                        style = MaterialTheme.typography.h6,
                        color = darkBlue
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "Please create new password",
                        style = MaterialTheme.typography.body1,
                        color = grey
                    )
                    Spacer(modifier = Modifier.height(32.dp))

                    //Enter new password
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = newPassword,
                        onValueChange = { text ->
                            newPassword = text
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        label = {
                            if (isNewPasswordError) {
                                Text(text = newPasswordErrorLabel)
                            } else {
                                Text(text = "Enter new password")
                            }
                        },
                        singleLine = true,
                        isError = isNewPasswordError,
                        visualTransformation = if (newPasswordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (newPasswordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff
                            val description =
                                if (newPasswordVisible) "Hide password" else "Show password"
                            IconButton(onClick = {
                                newPasswordVisible =
                                    !newPasswordVisible
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
                    Spacer(modifier = Modifier.height(16.dp))

                    //Confirm password
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = confirmPassword,
                        onValueChange = { text ->
                            confirmPassword = text
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        label = {
                            if (isConfirmPasswordError) {
                                Text(text = confirmPasswordErrorLabel)
                            } else {
                                Text(text = "Confirm new password")
                            }
                        },
                        singleLine = true,
                        isError = isConfirmPasswordError,
                        visualTransformation = if (confirmPasswordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (confirmPasswordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff
                            val description =
                                if (confirmPasswordVisible) "Hide password" else "Show password"
                            IconButton(onClick = {
                                confirmPasswordVisible =
                                    !confirmPasswordVisible
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
                    Spacer(modifier = Modifier.height(24.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            tint = if (isEightCharactersLong) brightGreen else red,
                            painter = if (isEightCharactersLong)
                                painterResource(id = R.drawable.ic_valid)
                            else
                                painterResource(
                                    id = R.drawable.ic_invalid
                                ),
                            contentDescription = "valid status icon"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Must be at least 8 characters long")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            tint = if (hasAtLeastOneUpperCaseLetter) brightGreen else red,
                            painter = if (hasAtLeastOneUpperCaseLetter)
                                painterResource(id = R.drawable.ic_valid)
                            else
                                painterResource(
                                    id = R.drawable.ic_invalid
                                ),
                            contentDescription = "valid status icon"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Include at least 1 uppercase letter")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            tint = if (hasAtLeastOneSpecialCharacter) brightGreen else red,
                            painter = if (hasAtLeastOneSpecialCharacter)
                                painterResource(id = R.drawable.ic_valid)
                            else
                                painterResource(
                                    id = R.drawable.ic_invalid
                                ),
                            contentDescription = "valid status icon"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Include at least 1 special character (!@#$%^&*)")
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            tint = if (hasAtLeastOneLowerCaseLetter) brightGreen else red,
                            painter = if (hasAtLeastOneLowerCaseLetter)
                                painterResource(id = R.drawable.ic_valid)
                            else
                                painterResource(
                                    id = R.drawable.ic_invalid
                                ),
                            contentDescription = "valid status icon"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Include at least 1 lowercase letter")
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    //Update

                    when (uiState.viewModelResult) {
                        ViewModelResult.INITIAL, ViewModelResult.ERROR -> {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(54.dp),
                                enabled = newPassword.isNotEmpty() && confirmPassword.isNotEmpty(),
                                onClick = {
                                    newPasswordErrorLabel = ""
                                    isNewPasswordError = false
                                    confirmPasswordErrorLabel = ""
                                    isConfirmPasswordError = false

                                    val trimmedNewPassword = newPassword.trim()
                                    val trimmedConfirmedPassword = confirmPassword.trim()

                                    //Basic validator
                                    if (trimmedNewPassword == trimmedConfirmedPassword) {
                                        viewModel.updateProfile(newPassword = trimmedNewPassword)
                                    } else {
                                        newPasswordErrorLabel = "Passwords do not match!"
                                        isNewPasswordError = true
                                        confirmPasswordErrorLabel = "Passwords do not match!"
                                        isConfirmPasswordError = true
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = MaterialTheme.colors.primary
                                )
                            ) {
                                Text(text = "Update")
                            }
                        }
                        ViewModelResult.LOADING, ViewModelResult.SUCCESS -> {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp,
                                modifier = Modifier.height(54.dp)
                            )
                            if (uiState.viewModelResult == ViewModelResult.SUCCESS) {
                                showSuccessDialog = true
                            }
                        }
                    }



                    Spacer(modifier = Modifier.height(24.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                        Text(modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                goToLoginScreen()
                            }
                            .padding(8.dp),
                            text = "Back to Login",
                            color = darkBlue
                        )
                    }
                    Spacer(modifier = Modifier.height(24.dp))

                }
            }

        }
    }



    if (showSuccessDialog) {
        Dialog(
            onDismissRequest = {
                goToLoginScreen()
            },
            properties = DialogProperties(
                dismissOnClickOutside = false
            )
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(top = 32.dp)

            ) {
                item {
                    Success(
                        title = "Successful",
                        message = uiState.message ?: "Changed Successfully",
                        onOkayPressed = {
                            goToLoginScreen()
                        },
                        buttonText = "Go To Dashboard"
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ForgotPasswordPreview() {
    CupidCustomerAppTheme {
        //NewPassword()
    }
}