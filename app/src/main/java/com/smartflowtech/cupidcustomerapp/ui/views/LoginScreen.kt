package com.smartflowtech.cupidcustomerapp.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.lightGrey

@Composable
fun LoginScreen(goToHomeScreen: () -> Unit, goToForgotPasswordScreen: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        contentAlignment = Alignment.TopCenter
    ) {
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
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                placeholder = { Text(text = "Email", style = MaterialTheme.typography.body1) },
                onValueChange = {},
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lightGrey,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            var passwordVisible by remember { mutableStateOf(false) }
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = "",
                placeholder = { Text(text = "Password", style = MaterialTheme.typography.body1) },
                onValueChange = {},
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description, tint = Color.LightGray)
                    }
                },
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lightGrey,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                onClick = { goToHomeScreen() },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Forgot password?")
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                Text(text = "Not me?")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CupidCustomerAppTheme {
        LoginScreen({}, {})
    }
}