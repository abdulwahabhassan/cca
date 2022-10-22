package com.smartflowtech.cupidcustomerapp.ui.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.theme.lightGrey

@Composable
@Preview(showBackground = true)
fun Profile() {

    // Visibility and input text
    var firstName by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    // Error and labels
    var firstNameError by rememberSaveable { mutableStateOf(false) }
    var lastNameError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var firstNameErrorLabel by rememberSaveable { mutableStateOf("") }
    var lastNameErrorLabel by rememberSaveable { mutableStateOf("") }
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.img_person_3),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop
                    )
                    Icon(
                        modifier = Modifier
                            .clickable {
                                //do something
                            }
                            .align(Alignment.BottomEnd)
                            .padding(start = 60.dp, top = 60.dp),
                        painter = painterResource(id = R.drawable.ic_edit_profile),
                        contentDescription = "Edit icon",
                        tint = Color.Unspecified
                    )
                }
                Column {
                    Text(
                        text = "Hassan Abdulwahab",
                        color = darkBlue,
                        fontSize = 18.sp
                    )
                    Text(text = "hassan@smartflowtech.com", color = grey)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            //First name
            TextField(
                modifier = Modifier
                    .fillMaxWidth(),
                value = firstName,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { text ->
                    firstName = text
                },
                singleLine = true,
                label = {
                    if (firstNameError) {
                        Text(text = firstNameErrorLabel)
                    } else {
                        Text(text = "First Name")
                    }
                },
                isError = firstNameError,
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

            //Last name
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = lastName,
                onValueChange = { text ->
                    lastName = text
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = {
                    if (lastNameError) {
                        Text(text = lastNameErrorLabel)
                    } else {
                        Text(text = "Last Name")
                    }
                },
                singleLine = true,
                isError = lastNameError,
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

            //Email
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { text ->
                    email = text
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = {
                    if (emailError) {
                        Text(text = emailErrorLabel)
                    } else {
                        Text(text = "Email")
                    }
                },
                singleLine = true,
                isError = emailError,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lightGrey,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            //Save Changes
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty(),
                onClick = {
                    firstNameErrorLabel = ""
                    firstNameError = false
                    lastNameErrorLabel = ""
                    lastNameError = false
                    emailErrorLabel = ""
                    emailError = false

                    val trimmedFirstName = firstName.trim()
                    val trimmedLastName = lastName.trim()
                    val trimmedEmail = email.trim()
                    //do something
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text(text = "Save Changes")
            }
            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}