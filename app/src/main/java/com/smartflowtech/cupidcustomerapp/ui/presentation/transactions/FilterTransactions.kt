package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.grey

@Composable
fun FilterTransactions() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
        Text(
            text = "Filter",
            color = Color.Black,
            fontFamily = AthleticsFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn() {
            listOf("Date", "Status", "Product").forEach { filter ->
                item {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp),
                        text = filter,
                        color = grey,
                        fontFamily = AthleticsFontFamily
                    )
                }
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun FilterTransactionsPreview() {
    FilterTransactions()
}