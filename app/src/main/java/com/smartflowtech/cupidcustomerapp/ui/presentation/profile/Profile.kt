package com.smartflowtech.cupidcustomerapp.ui.presentation.profile

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter
import kotlinx.coroutines.launch

@Composable
fun Profile(
    userFullName: String,
    userName: String,
    onUploadImageClicked: () -> Unit,
    onProfileUpdateSuccess: () -> Unit,
    profilePicture: String,
    onBackPressed: () -> Unit,
    updateProfile: suspend (firstname: String, lastname: String, email: String) -> UpdateProfileState
) {

    BackHandler(true) {
        onBackPressed()
    }

    // Visibility and input text
    var firstname by rememberSaveable { mutableStateOf("") }
    var lastname by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    // Error and labels
    var firstNameError by rememberSaveable { mutableStateOf(false) }
    var lastNameError by rememberSaveable { mutableStateOf(false) }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var firstNameErrorLabel by rememberSaveable { mutableStateOf("") }
    var lastNameErrorLabel by rememberSaveable { mutableStateOf("") }
    var emailErrorLabel by rememberSaveable { mutableStateOf("") }

    val scaffoldState = rememberScaffoldState()
    var showLoadingIndicator by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    fun resetErrorsAndLabels() {
        firstNameErrorLabel = ""
        firstNameError = false
        lastNameErrorLabel = ""
        lastNameError = false
        emailErrorLabel = ""
        emailError = false
    }

    fun validateEmail(email: String) {
        if (email.isEmpty() ||
            !email.contains("@") ||
            !email.contains(".")
        ) {
            emailErrorLabel = "Input valid email"
            emailError = true
        }
    }

    fun doUpdateProfile(firstNameInput: String, lastNameInput: String, emailInput: String) {
        coroutineScope.launch {
            val updateProfileState = updateProfile(
                firstNameInput,
                lastNameInput,
                emailInput
            )
            when (updateProfileState.viewModelResult) {
                ViewModelResult.ERROR -> {
                    showLoadingIndicator = false
                    if (
                        updateProfileState.message?.isNotEmpty() == true
                    ) {
                        if (scaffoldState.snackbarHostState
                                .currentSnackbarData == null
                        ) {
                            scaffoldState.snackbarHostState.showSnackbar(
                                message = updateProfileState.message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                }
                ViewModelResult.SUCCESS -> {
                    onProfileUpdateSuccess()
                }
                else -> {}
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding()
            .imePadding()
            .padding(top = 40.dp),
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

        LazyColumn(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
        ) {
            item {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box() {
                        Image(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .clipToBounds()
                                .clickable(false) { },
                            painter = if (profilePicture.isEmpty())
                                painterResource(id = R.drawable.ic_avatar)
                            else
                                rememberAsyncImagePainter(profilePicture),
                            contentDescription = "Avatar",
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(start = 52.dp, top = 52.dp)
                                .clipToBounds()
                                .clip(CircleShape)
                                .clickable {
                                    onUploadImageClicked()
                                },
                            painter = painterResource(id = R.drawable.ic_edit_profile),
                            contentDescription = "Edit icon",
                            tint = Color.Unspecified
                        )
                    }
                    Column {
                        Text(
                            text = userFullName,
                            color = darkBlue,
                            fontSize = 18.sp
                        )

                        if (userName.isNotEmpty()) {
                            Text(text = "@$userName", color = grey)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                //First name
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = firstname,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    onValueChange = { text ->
                        firstname = text
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
                    value = lastname,
                    onValueChange = { text ->
                        lastname = text
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

                if (showLoadingIndicator) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.height(54.dp)
                    )
                } else {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp),
                        enabled = firstname.isNotEmpty() &&
                                lastname.isNotEmpty() &&
                                email.isNotEmpty(),
                        onClick = {

                            resetErrorsAndLabels()

                            val trimmedEmail = email.trim()
                            validateEmail(trimmedEmail)

                            if (!emailError) {
                                focusManager.clearFocus()
                                showLoadingIndicator = true
                                doUpdateProfile(
                                    firstname.trim().capitalizeFirstLetter(),
                                    lastname.trim().capitalizeFirstLetter(),
                                    trimmedEmail
                                )
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colors.primary
                        )
                    ) {
                        Text(text = "Save Changes")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewProfile() {
//    Profile({}, {}, {})
}