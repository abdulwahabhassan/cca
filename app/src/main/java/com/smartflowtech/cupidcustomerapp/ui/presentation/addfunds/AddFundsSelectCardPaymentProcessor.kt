package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.smartflowtech.cupidcustomerapp.model.CardPaymentProcessor
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.PaymentError
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.PaymentSuccess
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddFundsSelectCardPaymentProcessor(
    modalBottomSheetState: ModalBottomSheetState,
    onBackPressed: () -> Unit,
    onDismissErrorDialog: () -> Unit,
    onDismissSuccessDialog: () -> Unit
) {

    var selectedCardProcessor by remember { mutableStateOf("") }
    var showCardDetailsForm by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var transactionReference by remember { mutableStateOf("") }
    val ctx = LocalContext.current

    BackHandler(modalBottomSheetState.isVisible) {
        onBackPressed()
    }

    LazyColumn(modifier = Modifier) {
        items(
            listOf(
                CardPaymentProcessor.PAYSTACK
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
                            transactionReference = reference
                            showSuccess = true
                            //send ref to business owner for confirmation
                            Toast.makeText(
                                ctx,
                                "Success!",
                                Toast.LENGTH_LONG
                            ).show()
                        },
                        onPaymentError = { message, reference ->
                            showCardDetailsForm = false
                            selectedCardProcessor = ""
                            errorMessage = message
                            transactionReference = reference
                            showError = true
                            Toast.makeText(
                                ctx,
                                "Error!",
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
                onDismissSuccessDialog()
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
                    PaymentSuccess(
                        message = "Payment Successful",
                        info = "Ref: $transactionReference\n" +
                                "You have successfully funded your wallet!",
                        onOkayPressed = {
                            showSuccess = false
                            onDismissSuccessDialog()
                        }
                    )
                }
            }
        }
    }

    if (showError) {
        Dialog(
            onDismissRequest = {
                showError = false
                onDismissErrorDialog()
            },
        ) {
            LazyColumn(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                item {
                    PaymentError(
                        message = "Transaction Failed",
                        info = "Ref: $transactionReference\n" +
                                "$errorMessage ",
                        onCloseDialog = {
                            showError = false
                            onDismissErrorDialog()
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun AddFundsSelectedCardPaymentProcessorPreview() {
    CupidCustomerAppTheme {
        AddFundsSelectCardPaymentProcessor(
            ModalBottomSheetState(
                ModalBottomSheetValue.Expanded
            ),
            {},
            {},
            {}
        )
    }
}