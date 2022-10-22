package com.smartflowtech.cupidcustomerapp.ui.presentation.password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.theme.lightGrey

@Composable
fun ResetPassword() {


    var email by rememberSaveable { mutableStateOf("") }

    // Error and labels
    var isEmailError by rememberSaveable { mutableStateOf(false) }
    var emailErrorLabel by rememberSaveable { mutableStateOf("") }

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
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text(text = "Proceed")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewResetPassword() {
    ResetPassword()
}