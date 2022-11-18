package com.smartflowtech.cupidcustomerapp.ui.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.model.domain.Period
import com.smartflowtech.cupidcustomerapp.model.domain.CardHistoryPeriodFilterContext
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@Composable
fun PeriodFilter(
    onPeriodFilterSelected: (String, CardHistoryPeriodFilterContext) -> Unit,
    context: CardHistoryPeriodFilterContext,
    periods: List<String> = Util.defaultPeriods
) {
    Column(Modifier.fillMaxSize()) {

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Filter",
            color = Color.Black,
            fontFamily = AthleticsFontFamily,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(periods) { period ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onPeriodFilterSelected(period, context)
                    }
                    .padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = when (period) {
                            Period.TODAY.name -> "Today"
                            Period.ONE_WEEK.name -> "Last 7 Days"
                            Period.ONE_MONTH.name -> "Last 30 Days"
                            Period.ONE_YEAR.name -> "Last 12 months"
                            else -> period
                        },
                        modifier = Modifier.padding(vertical = 10.dp),
                        color = grey
                    )
                }
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewDaysFilter() {
    CupidCustomerAppTheme {
        PeriodFilter(onPeriodFilterSelected = { _, _-> }, CardHistoryPeriodFilterContext.DEFAULT)
    }
}