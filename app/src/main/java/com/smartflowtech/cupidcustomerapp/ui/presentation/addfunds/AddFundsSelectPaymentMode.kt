package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smartflowtech.cupidcustomerapp.model.domain.PaymentMode
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme

@Composable
fun AddFundsSelectPaymentMode(
    onSelectPaymentMode: (paymentMode: PaymentMode) -> Unit,
) {

    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.Start
    ) {

        AnimatedVisibility(
            visible = true,
            enter = slideInVertically(spring()) { it / 10000 },
            exit = slideOutVertically(spring()) { it / 10000 }
        ) {

        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                listOf(
                    PaymentMode.CARD
                )
            ) { paymentMode ->
                AddFundsPaymentMode(
                    paymentMode
                ) { mode: PaymentMode ->
                    onSelectPaymentMode(mode)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun AddFundsSelectPaymentModePreview() {
    CupidCustomerAppTheme {
        AddFundsSelectPaymentMode(onSelectPaymentMode = {},)
    }
}