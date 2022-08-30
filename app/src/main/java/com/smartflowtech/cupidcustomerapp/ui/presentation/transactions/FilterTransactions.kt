package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.black
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilterTransactions(
    dateFilter: String,
    statusFilter: String,
    productFilter: String,
    onCustomSearchClicked: () -> Unit,
    onDateFilterSelected: (String) -> Unit,
    onStatusFilterSelected: (String) -> Unit,
    onProductFilterSelected: (String) -> Unit
) {

    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Filter Transactions",
            color = Color.Black,
            fontFamily = AthleticsFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(24.dp))
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp)) {
            mapOf(
                "Date" to listOf("Today", "7 days ago", "15 days ago", "30 days ago"),
                "Status" to listOf("Completed", "Pending", "Failed"),
                "Product" to listOf("AGO", "DPK", "PMS")
            ).forEach { (category, filters) ->

                stickyHeader {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                            .padding(vertical = 16.dp),
                        text = category,
                        color = black,
                        fontFamily = AthleticsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(filters) { filter ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = when (category) {
                                "Date" -> filter == dateFilter
                                "Status" -> filter == statusFilter
                                "Product" -> filter == productFilter
                                else -> {
                                    false
                                }
                            },
                            onClick = {
                                when (category) {
                                    "Date" -> onDateFilterSelected(filter)
                                    "Status" -> onStatusFilterSelected(filter)
                                    "Product" -> onProductFilterSelected(filter)
                                }
                            },
                            colors = RadioButtonDefaults.colors(selectedColor = darkBlue)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = filter,
                            color = grey,
                            fontFamily = AthleticsFontFamily
                        )
                    }
                }

//                if (category == "Date") {
//                    item {
//                        Row(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(start = 16.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                modifier = Modifier.padding(vertical = 8.dp),
//                                text = "Custom search",
//                                color = grey,
//                                fontFamily = AthleticsFontFamily
//                            )
//                            Spacer(modifier = Modifier.width(4.dp))
//                            IconButton(onClick = {
//                                onCustomSearchClicked()
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Rounded.ArrowForward,
//                                    contentDescription = "Forward arrow",
//                                    tint = grey
//                                )
//                            }
//                        }
//                    }
//                }

            }

        }
    }
}


@Composable
@Preview(showBackground = true)
fun FilterTransactionsPreview() {
    FilterTransactions("", "", "", {}, {}, {}, {})
}