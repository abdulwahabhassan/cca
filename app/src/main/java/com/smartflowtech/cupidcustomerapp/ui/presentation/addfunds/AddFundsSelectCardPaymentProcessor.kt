package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.model.CardPaymentProcessor
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme

@Composable
fun AddFundsSelectCardPaymentProcessor() {

    var selectedCardProcessor by remember { mutableStateOf(CardPaymentProcessor.PAYSTACK) }

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
                    selectedCardProcessor = cardProcessor
                },
                isSelected = selectedCardProcessor == cardProcessor
            )
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