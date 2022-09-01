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
import com.smartflowtech.cupidcustomerapp.model.Product
import com.smartflowtech.cupidcustomerapp.model.Status
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.black
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilterTransactions(
    daysFilter: Long,
    onCustomSearchClicked: () -> Unit,
    completedStatusFilter: Boolean,
    failedStatusFilter: Boolean,
    pendingStatusFilter: Boolean,
    dpkProductFilter: Boolean,
    pmsProductFilter: Boolean,
    agoProductFilter: Boolean,
    onDaysFilterSelected: (String) -> Unit,
    onStatusFilterSelected: (Boolean, String) -> Unit,
    onProductFilterSelected: (Boolean, String) -> Unit
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
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)) {
            mapOf(
                "Date" to listOf("0", "7", "15", "30", "182", "365", "730"),
                "Status" to listOf(Status.COMPLETED.name, Status.PENDING.name, Status.FAILED.name),
                "Product" to listOf(Product.AGO.name, Product.DPK.name, Product.PMS.name)
            ).forEach { (category, filters) ->

                stickyHeader {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                            .padding(vertical = 12.dp),
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
                            .padding(start = 16.dp, top = 6.dp, bottom = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (category == "Status" || category == "Product") {
                            Checkbox(
                                modifier = Modifier.size(36.dp),
                                checked = when (filter) {
                                    Status.COMPLETED.name -> completedStatusFilter
                                    Status.FAILED.name -> failedStatusFilter
                                    Status.PENDING.name -> pendingStatusFilter
                                    Product.DPK.name -> dpkProductFilter
                                    Product.AGO.name -> agoProductFilter
                                    Product.PMS.name -> pmsProductFilter
                                    else -> false
                                },
                                onCheckedChange = { bool ->
                                    when (category) {
                                        "Status" -> onStatusFilterSelected(bool, filter)
                                        "Product" -> onProductFilterSelected(bool, filter)
                                    }
                                },
                                colors = CheckboxDefaults.colors(checkedColor = darkBlue)
                            )
                        } else {
                            RadioButton(
                                modifier = Modifier.size(36.dp),
                                selected = filter == daysFilter.toString(),
                                onClick = {
                                    onDaysFilterSelected(filter)
                                },
                                colors = RadioButtonDefaults.colors(selectedColor = darkBlue)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = if (category == "Date") {
                                if (filter.toLong() == 0L) {
                                    "Today"
                                } else if (filter.toLong() == 7L) {
                                    "1 week ago"
                                } else if (filter.toLong() == 15L) {
                                    "2 weeks ago"
                                } else if (filter.toLong() == 30L) {
                                    "1 month ago"
                                } else if (filter.toLong() == 182L) {
                                    "6 months ago"
                                } else if (filter.toLong() == 365L) {
                                    "1 year ago"
                                } else if (filter.toLong() == 730L) {
                                    "2 years ago"
                                } else {
                                    "All"
                                }
                            } else if (category == "Status") {
                                filter.capitalizeFirstLetter()
                            } else {
                                filter
                            },
                            color = grey,
                            fontFamily = AthleticsFontFamily
                        )
                    }
                }

                //Not yet
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
    //FilterTransactions(1, "", "", {}, {}, {}, {})
}