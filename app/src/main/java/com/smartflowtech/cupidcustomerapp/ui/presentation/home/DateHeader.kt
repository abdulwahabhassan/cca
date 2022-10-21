package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme

@Composable
fun Header(title: String, bgColor: Color = Color.White, fontWeight: FontWeight = FontWeight.Normal) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = bgColor)
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp),
        text = title,
        style = MaterialTheme.typography.caption,
        fontWeight = fontWeight
    )
}

@Composable
@Preview(showBackground = true)
fun TransactionDateHeaderPreview() {
    CupidCustomerAppTheme {
        Header(title = "Jun 24, 2022")
    }
}