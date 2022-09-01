package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun AddFundsScreen(onBackPressed: () -> Unit) {

    var amount by rememberSaveable { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    var visible by rememberSaveable { mutableStateOf(true) }
    visible =
        bottomSheetState.direction >= 0f && bottomSheetState.currentValue == BottomSheetValue.Collapsed

    Scaffold(
    ) { paddingValues ->

        BottomSheetScaffold(
            modifier = Modifier.padding(paddingValues),
            scaffoldState = bottomSheetScaffoldState,
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent,
            sheetPeekHeight = 0.dp,
            snackbarHost = {
                SnackbarHost(it) { data ->
                    Snackbar(
                        backgroundColor = transparentPurple,
                        contentColor = darkBlue,
                        snackbarData = data
                    )
                }
            },
            sheetContent = {

                AnimatedVisibility(
                    visible = !visible,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {
                            if (bottomSheetState.isExpanded) {
                                coroutineScope.launch {
                                    bottomSheetState.collapse()
                                }
                            }
                        }) {}

                        Text(
                            text = "Fund Wallet",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        IconButton(onClick = {}) {}
                    }
                }

                Column(
                    Modifier
                        .padding(top = 2.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .fillMaxSize(),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(40.dp)
                            .padding(bottom = 24.dp),
                        painter = painterResource(id = R.drawable.ic_bottom_sheet_handle_inactive),
                        contentDescription = "Bottom sheet handle",
                        tint = Color.Unspecified
                    )

                    Column(modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {
                        Text(
                            "This service is currently unavailable",
                            color = purple,
                            modifier = Modifier
                                .background(
                                    color = transparentPurple,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .padding(8.dp),
                            textAlign = TextAlign.Center
                        )
                    }

                }

            }) { paddingValues ->

            Column(
                Modifier
                    .fillMaxSize()
                    .background(brush = Brush.horizontalGradient(gradientBluePurple))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 32.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {

                    //Back button
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back arrow",
                            tint = Color.White,
                        )
                    }

                }

                AddFundsKeyPad(
                    displayValue = amount,
                    onDisplayValueUpdated = { newValue ->
                        amount = newValue
                    },
                    showSnackBar = { message ->
                        coroutineScope.launch {
                            bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                                message = message,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }

                )

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp)
                        .padding(horizontal = 16.dp),
                    onClick = {
                        if (amount.isNotEmpty() && amount != "0") {
                            coroutineScope.launch {
                                bottomSheetState.expand()
                            }
                        } else {
                            coroutineScope.launch {
                                bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                                    message = "Invalid amount",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }

                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text(text = "Proceed")
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AddFundsScreenPreview() {
    CupidCustomerAppTheme {
        //AddFundsScreen({}, {}, { true }, {})
    }
}