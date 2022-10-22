package com.smartflowtech.cupidcustomerapp.ui.presentation.password

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.theme.*


@Composable
fun ForgotPassword() {

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
    var isEightCharactersLong by rememberSaveable { mutableStateOf(false) }
    var hasAtLeastOneUpperCaseLetter by rememberSaveable { mutableStateOf(false) }
    var hasAtLeastOneSpecialCharacter by rememberSaveable { mutableStateOf(false) }
    var hasAtLeastOneLowerCaseLetter by rememberSaveable { mutableStateOf(false) }

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
                        //
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
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
                Text(modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        //do something
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

@Composable
@Preview(showBackground = true)
fun ForgotPasswordPreview() {
    CupidCustomerAppTheme {
        ForgotPassword()
    }
}