package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.smartflowtech.cupidcustomerapp.model.CardPaymentProcessor
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.Success
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme

@Composable
fun AddFundsSelectCardPaymentProcessor() {

    var selectedCardProcessor by remember { mutableStateOf("") }
    var showCardDetailsForm by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    val ctx = LocalContext.current

    LazyColumn(modifier = Modifier) {
        items(
            listOf(
                CardPaymentProcessor.PAYSTACK,
                CardPaymentProcessor.FLUTTERWAVE
            )
        ) { cardProcessor ->
            AddFundsCardPaymentProcessor(
                cardPaymentProcessor = cardProcessor,
                onClick = { cardProcessor ->
                    selectedCardProcessor = cardProcessor.name
                    showCardDetailsForm = true
                },
                isSelected = selectedCardProcessor == cardProcessor.name
            )
        }

    }

    if (showCardDetailsForm && selectedCardProcessor.equals("Paystack", true)) {
        Dialog(
            onDismissRequest = {
                showCardDetailsForm = false
                selectedCardProcessor = ""
            },
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
                    AddFundsCardPaymentDetailsForm(
                        onBackPressed = {
                            showCardDetailsForm = false
                            selectedCardProcessor = ""
                        },
                        onPaymentSuccess = { reference ->
                            showCardDetailsForm = false
                            selectedCardProcessor = ""
                            //send ref to business owner for confirmation
                            Toast.makeText(
                                ctx,
                                "Successful payment $reference",
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        onPaymentError = { message, reference ->
                            Toast.makeText(
                                ctx,
                                "Error due to $message, Ref: $reference",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    )
                }
            }
        }
    }

    if (showSuccess) {
        Dialog(
            onDismissRequest = {
                showSuccess = false
                selectedCardProcessor = ""
                showCardDetailsForm = false
            },
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
                        message = "Payment Successful",
                        info = "You have successfully funded your wallet"
                    ) {

                    }
                }
            }
        }
    }

    if (showError) {
        Dialog(
            onDismissRequest = {
                showError = false
                selectedCardProcessor = ""
                showCardDetailsForm = false
            },
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
                        message = "Payment Successful",
                        info = "You have successfully funded your wallet"
                    ) {

                    }
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun AddFundsSelectedCardPaymentProcessorPreview() {
    CupidCustomerAppTheme {
        AddFundsSelectCardPaymentProcessor()
    }
}