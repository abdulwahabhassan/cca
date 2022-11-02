package com.smartflowtech.cupidcustomerapp.ui.presentation.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.model.domain.*
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.deepBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeEachWord
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilterTransactions(
    appConfigPreferences: AppConfigPreferences,
    onFilterSaveClicked: (String, Map<String, Boolean>) -> Unit,
    onBackPressed: () -> Unit
) {

    val daysFilter =
        remember { mutableStateOf(appConfigPreferences.periodFilter) }
    val completedStatusFilter =
        remember { mutableStateOf(appConfigPreferences.completedStatusFilter) }
    val pendingStatusFilter =
        remember { mutableStateOf(appConfigPreferences.pendingStatusFilter) }
    val failedStatusFilter =
        remember { mutableStateOf(appConfigPreferences.failedStatusFilter) }
    val dpkProductFilter =
        remember { mutableStateOf(appConfigPreferences.dpkProductFilter) }
    val agoProductFilter =
        remember { mutableStateOf(appConfigPreferences.agoProductFilter) }
    val pmsProductFilter =
        remember { mutableStateOf(appConfigPreferences.pmsProductFilter) }

    Column(
        Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Filter Preferences",
            color = Color.Black,
            fontFamily = AthleticsFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
            mapOf(
                Category.DATE to listOf(
                    Period.TODAY.name,
                    Period.ONE_WEEK.name,
                    Period.TWO_WEEKS.name,
                    Period.ONE_MONTH.name,
                    Period.SIX_MONTHS.name,
                    Period.ONE_YEAR.name,
                    Period.TWO_YEARS.name
                ),
                Category.STATUS to listOf(
                    Status.COMPLETED.name,
                    Status.PENDING.name,
                    Status.FAILED.name
                ),
                Category.PRODUCT to listOf(
                    Product.AGO.name,
                    Product.DPK.name,
                    Product.PMS.name
                )
            ).forEach { (category, filters) ->

                stickyHeader {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                            .padding(vertical = 12.dp),
                        text = category.name.capitalizeFirstLetter(),
                        color = deepBlue,
                        fontFamily = AthleticsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                items(filters) { filter ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, top = 2.dp, bottom = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        when (category) {
                            Category.STATUS, Category.PRODUCT -> {
                                Checkbox(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(RoundedCornerShape(2.dp))
                                        .clipToBounds(),
                                    checked = when (filter) {
                                        Status.COMPLETED.name -> completedStatusFilter.value
                                        Status.FAILED.name -> failedStatusFilter.value
                                        Status.PENDING.name -> pendingStatusFilter.value
                                        Product.DPK.name -> dpkProductFilter.value
                                        Product.AGO.name -> agoProductFilter.value
                                        Product.PMS.name -> pmsProductFilter.value
                                        else -> false
                                    },
                                    onCheckedChange = { bool ->
                                        when (filter) {
                                            Status.COMPLETED.name -> {
                                                completedStatusFilter.value = bool
                                            }
                                            Status.PENDING.name -> {
                                                pendingStatusFilter.value = bool
                                            }
                                            Status.FAILED.name -> {
                                                failedStatusFilter.value = bool
                                            }
                                            Product.DPK.name -> {
                                                dpkProductFilter.value = bool
                                            }
                                            Product.PMS.name -> {
                                                pmsProductFilter.value = bool
                                            }
                                            Product.AGO.name -> {
                                                agoProductFilter.value = bool
                                            }
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = darkBlue)
                                )
                            }

                            else -> {
                                RadioButton(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(RoundedCornerShape(50))
                                        .padding(8.dp)
                                        .clipToBounds(),
                                    selected = daysFilter.value == filter,
                                    onClick = {
                                        daysFilter.value = filter
                                    },
                                    colors = RadioButtonDefaults.colors(selectedColor = darkBlue)
                                )
                            }

                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = when (category) {
                                Category.DATE -> {
                                    filter.capitalizeEachWord()
                                }
                                Category.STATUS -> {
                                    filter.capitalizeFirstLetter()
                                }
                                Category.PRODUCT -> {
                                    filter
                                }
                            },
                            color = grey,
                            fontFamily = AthleticsFontFamily
                        )
                    }
                }

//                if (category == Category.DATE) {
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
//
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

            item {
                Button(
                    onClick = {
                        onBackPressed().also {
                            onFilterSaveClicked(
                                daysFilter.value,
                                mapOf(
                                    Status.COMPLETED.name to completedStatusFilter.value,
                                    Status.FAILED.name to failedStatusFilter.value,
                                    Status.PENDING.name to pendingStatusFilter.value,
                                    Product.AGO.name to agoProductFilter.value,
                                    Product.DPK.name to dpkProductFilter.value,
                                    Product.PMS.name to pmsProductFilter.value
                                )
                            )
                        }
                    },
                    modifier = Modifier
                        .padding(vertical = 24.dp, horizontal = 8.dp)
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(10.dp),
                ) {
                    Text(text = "Save")
                }

            }

        }
    }
}


@Composable
@Preview(showBackground = true)
fun FilterTransactionsPreview() {
    //FilterTransactions(1, "", "", {}, {}, {}, {})
}