package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.paystack.android.Paystack
import co.paystack.android.PaystackSdk
import co.paystack.android.Transaction
import co.paystack.android.model.Card
import co.paystack.android.model.Charge
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.lightGrey
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.findActivity

@Composable
fun AddFundsCardPaymentDetailsForm(
    onBackPressed: () -> Unit,
    onPaymentSuccess: (reference: String) -> Unit,
    onPaymentError: (message: String, reference: String) -> Unit
) {

    var cardNumber by rememberSaveable { mutableStateOf("5060666666666666666") }
    var cvv by rememberSaveable { mutableStateOf("123") }
    var month by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("") }
    var cardNumberErrorLabel by rememberSaveable { mutableStateOf("") }
    var cvvErrorLabel by rememberSaveable { mutableStateOf("") }
    var monthErrorLabel by rememberSaveable { mutableStateOf("") }
    var yearErrorLabel by rememberSaveable { mutableStateOf("") }
    var isCardNumberError by rememberSaveable { mutableStateOf(false) }
    var isCvvError by rememberSaveable { mutableStateOf(false) }
    var isMonthError by rememberSaveable { mutableStateOf(false) }
    var isYearError by rememberSaveable { mutableStateOf(false) }
    val ctx = LocalContext.current
    var showLoadingIndicator by remember { mutableStateOf(false)}

    fun chargeCardWithPayStack(card: Card) {
        if (card.isValid) {
            val charge = Charge()
                .setAmount((100 * 100).toInt())
                .setEmail("devhassan.org@gmail.com")
                .setCard(card)

            PaystackSdk.chargeCard(
                ctx.findActivity(),
                charge,
                object : Paystack.TransactionCallback {
                    override fun onSuccess(transaction: Transaction?) {
                        onPaymentSuccess(transaction?.reference ?: "No ref")
                    }

                    override fun beforeValidate(transaction: Transaction?) {
                        Toast.makeText(
                            ctx,
                            "Before validate ${transaction?.reference}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onError(
                        error: Throwable?,
                        transaction: Transaction?
                    ) {
                        onPaymentError(
                            error?.message ?: "No message",
                            transaction?.reference ?: "No ref"
                        )
                    }

                })
        } else {
            Toast.makeText(
                ctx,
                "Invalid Card: Please select a valid card",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Text(
                "Input Payment Details", color = Color.Black,
                fontFamily = AthleticsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            //Card Number
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = cardNumber,
                onValueChange = { text ->
                    cardNumber = text
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = {
                    if (isCardNumberError) {
                        Text(text = cardNumberErrorLabel, fontSize = 13.sp)
                    } else {
                        Text(text = "Card Number", fontSize = 13.sp)
                    }
                },
                singleLine = true,
                isError = isCardNumberError,
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

            Row(modifier = Modifier.fillMaxWidth()) {

                //Month
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f, true),
                    value = month,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { text ->
                        if (text.length <= 2) {
                            month = text
                        }
                    },
                    singleLine = true,
                    label = {
                        if (isMonthError) {
                            Text(text = monthErrorLabel, fontSize = 12.sp)
                        } else {
                            Text(text = "MM", fontSize = 12.sp)
                        }
                    },
                    isError = isMonthError,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = lightGrey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )

                //Year
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp)
                        .weight(1f, true),
                    value = year,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { text ->
                        if (text.length <= 4) {
                            year = text
                        }
                    },
                    singleLine = true,
                    label = {
                        if (isYearError) {
                            Text(text = yearErrorLabel, fontSize = 12.sp)
                        } else {
                            Text(text = "YYYY", fontSize = 12.sp)
                        }
                    },
                    isError = isYearError,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = lightGrey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )

                //CVV
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, true),
                    value = cvv,
                    onValueChange = { text ->
                        if (text.trim().length <= 4) {
                            cvv = text
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = {
                        if (isCvvError) {
                            Text(text = cvvErrorLabel, fontSize = 12.sp)
                        } else {
                            Text(text = "CVV", fontSize = 12.sp)
                        }
                    },
                    singleLine = true,
                    isError = isCvvError,
                    shape = RoundedCornerShape(10.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = lightGrey,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            if (showLoadingIndicator) {
                CircularProgressIndicator(
                    strokeWidth = 2.dp, modifier = Modifier
                        .height(54.dp)
                        .align(CenterHorizontally)
                )
            } else {
                //Pay button
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    enabled = cardNumber.isNotEmpty() && month.isNotEmpty() && year.isNotEmpty() && cvv.isNotEmpty(),
                    onClick = {
                        val trimmedCardNumber = cardNumber.trim()
                        val trimmedMonth = month.trim()
                        val trimmedYear = year.trim()
                        val trimmedCvv = cvv.trim()

                        if (!trimmedMonth.matches(Regex("\\d{2}")) || trimmedMonth.toInt() > 12) {
                            monthErrorLabel = "*"
                            isMonthError = true
                        } else {
                            monthErrorLabel = ""
                            isMonthError = false
                        }

                        if (!trimmedYear.matches(Regex("\\d{4}"))) {
                            yearErrorLabel = "*"
                            isYearError = true
                        } else {
                            yearErrorLabel = ""
                            isYearError = false
                        }

                        if (!isCardNumberError && !isMonthError && !isCvvError) {

                            showLoadingIndicator = true

                            val card = Card(
                                trimmedCardNumber,
                                trimmedMonth.toInt(),
                                trimmedYear.toInt(),
                                trimmedCvv
                            )

                            chargeCardWithPayStack(card)

                        } else {
                            Toast.makeText(
                                ctx,
                                "Please input valid card details",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(text = "Pay")
                }
            }
        }

        //Go back arrow
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onBackPressed()
            }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back arrow",
                    tint = darkBlue,
                )
            }
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Go back",
                color = darkBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }

}


@Preview()
@Composable
fun CardDetailsFormPreview() {
    CupidCustomerAppTheme {
        AddFundsCardPaymentDetailsForm({}, {}, {message, reference ->  })
    }
}
