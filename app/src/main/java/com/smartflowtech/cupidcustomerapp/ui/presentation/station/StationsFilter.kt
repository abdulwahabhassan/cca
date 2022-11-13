package com.smartflowtech.cupidcustomerapp.ui.presentation.station

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeFirstLetter
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import java.util.*

@Composable
fun StationFilter(
    onStationFilterSelected: (String) -> Unit
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
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(Util.getListOfStationsFilter()) { stationFilter ->
                Row(modifier = Modifier
                    .clickable {
                        onStationFilterSelected(stationFilter)
                    }
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)) {
                    Text(
                        text = stationFilter.lowercase(Locale.ROOT).capitalizeFirstLetter(),
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = grey
                    )
                }
            }
        }
    }

}