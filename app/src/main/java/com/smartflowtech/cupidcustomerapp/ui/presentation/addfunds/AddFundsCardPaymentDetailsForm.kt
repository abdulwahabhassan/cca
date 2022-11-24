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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.paystack.android.Paystack
import co.paystack.android.PaystackSdk
import co.paystack.android.Transaction
import co.paystack.android.model.Card
import co.paystack.android.model.Charge
import com.smartflowtech.cupidcustomerapp.model.response.PayStackPaymentData
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.lightGrey
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.findActivity
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun AddFundsCardPaymentDetailsForm(
    onBackPressed: () -> Unit,
    onPaymentSuccess: (reference: String) -> Unit,
    onPaymentError: (message: String, reference: String) -> Unit,
    amount: Int,
    initiatePayStackPayment: suspend (amount: Int) -> PayStackPaymentState,
    fundWalletAfterPayStackPayment: suspend (amount: Int, reference: String) -> FundWalletState,
) {

    var cardNumber by rememberSaveable { mutableStateOf("") }
    var cvv by rememberSaveable { mutableStateOf("") }
    var month by rememberSaveable { mutableStateOf("") }
    var year by rememberSaveable { mutableStateOf("") }
    val cardNumberErrorLabel by rememberSaveable { mutableStateOf("") }
    val cvvErrorLabel by rememberSaveable { mutableStateOf("") }
    var monthErrorLabel by rememberSaveable { mutableStateOf("") }
    var yearErrorLabel by rememberSaveable { mutableStateOf("") }
    val isCardNumberError by rememberSaveable { mutableStateOf(false) }
    val isCvvError by rememberSaveable { mutableStateOf(false) }
    var isMonthError by rememberSaveable { mutableStateOf(false) }
    var isYearError by rememberSaveable { mutableStateOf(false) }
    val ctx = LocalContext.current
    var showLoadingIndicator by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    fun initFreshPayStackTransaction(card: Card, data: PayStackPaymentData, userEmail: String) {

        val charge = Charge()
            .setCard(card)
            .setCurrency("NGN")
            .setSubaccount(data.paystackSubaccountCode)
            .setAmount(data.amountToPayKobo.toInt())
            .setEmail(userEmail)
            .setReference(data.ref)
            .setBearer(Charge.Bearer.subaccount)


        PaystackSdk.chargeCard(
            ctx.findActivity(),
            charge,
            object : Paystack.TransactionCallback {
                override fun onSuccess(transaction: Transaction?) {
                    coroutineScope.launch {
                        if (transaction?.reference != null) {
                            Timber.d("Transaction Ref -> ${transaction.reference}")
                            val fundWalletState = fundWalletAfterPayStackPayment(
                                data.originalAmount,
                                transaction.reference!!
                            )
                            when (fundWalletState.viewModelResult) {
                                ViewModelResult.SUCCESS -> {
                                    onPaymentSuccess(transaction.reference)
                                }
                                ViewModelResult.ERROR -> {
                                    onPaymentError(
                                        fundWalletState.message
                                            ?: "Your wallet could not be funded",
                                        transaction.reference
                                    )
                                }
                                else -> {}
                            }
                        } else {
                            onPaymentError(
                                "Transaction reference not found",
                                ""
                            )
                        }

                    }
                }

                override fun beforeValidate(transaction: Transaction?) {}

                override fun onError(
                    error: Throwable?,
                    transaction: Transaction?
                ) {
                    onPaymentError(
                        error?.message ?: "No message",
                        transaction?.reference ?: "No ref"
                    )
                    showLoadingIndicator = false
                }

            })
    }

    fun initiatePayStackPayment(card: Card) {
        if (card.isValid) {
            coroutineScope.launch {
                val paymentState = initiatePayStackPayment(amount)
                when (paymentState.viewModelResult) {
                    ViewModelResult.ERROR -> {
                        showLoadingIndicator = false
                        Toast.makeText(
                            ctx,
                            paymentState.message ?: "Oops! An error occurred!, Retry",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    ViewModelResult.SUCCESS -> {
                        paymentState.data?.let { data ->
                            initFreshPayStackTransaction(card, data, paymentState.userEmail ?: "")
                        } ?: Toast.makeText(
                            ctx,
                            paymentState.message ?: "Oops! Access code not found!",
                            Toast.LENGTH_LONG
                        ).show().also {
                            showLoadingIndicator = false
                        }

                    }
                    else -> {}
                }

            }

        } else {
            Toast.makeText(
                ctx,
                "Invalid Card! Please try a valid card",
                Toast.LENGTH_SHORT
            ).show()
            showLoadingIndicator = false
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

            Spacer(modifier = Modifier.height(24.dp))

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
                        showLoadingIndicator = true

                        val trimmedCardNumber = cardNumber.trim()
                        val trimmedMonth = month.trim()
                        val trimmedYear = year.trim()
                        val trimmedCvv = cvv.trim()

                        if (!trimmedMonth.matches(Regex("\\d{2}")) || trimmedMonth.toInt() > 12) {
                            monthErrorLabel = "MM"
                            isMonthError = true
                        } else {
                            monthErrorLabel = ""
                            isMonthError = false
                        }

                        if (!trimmedYear.matches(Regex("\\d{4}"))) {
                            yearErrorLabel = "YYYY"
                            isYearError = true
                        } else {
                            yearErrorLabel = ""
                            isYearError = false
                        }

                        if (!isCardNumberError && !isMonthError && !isCvvError) {

                            val card = Card(
                                trimmedCardNumber,
                                trimmedMonth.toInt(),
                                trimmedYear.toInt(),
                                trimmedCvv
                            )

                            initiatePayStackPayment(card)

                        } else {
                            Toast.makeText(
                                ctx,
                                "Please input valid card details",
                                Toast.LENGTH_SHORT
                            ).show()
                            showLoadingIndicator = false
                        }

                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                ) {
                    Text(text = "Pay")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        //Go back arrow
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { onBackPressed() }
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back arrow",
                tint = darkBlue,
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = "Go back",
                color = darkBlue,
                fontWeight = FontWeight.Bold
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun CardDetailsFormPreview() {
    CupidCustomerAppTheme {
        AddFundsCardPaymentDetailsForm(
            {},
            {},
            { message, reference -> },
            100,
            { PayStackPaymentState(ViewModelResult.SUCCESS) },
            { _, _ -> FundWalletState(ViewModelResult.SUCCESS) }
        )
    }
}
