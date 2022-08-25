package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import java.util.*

@Composable
fun TransactionDateHeader(date: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
        text = date,
        style = MaterialTheme.typography.caption
    )
}

@Composable
@Preview(showBackground = true)
fun TransactionDateHeaderPreview() {
    CupidCustomerAppTheme {
        TransactionDateHeader(date = "Jun 24, 2022")
    }
}