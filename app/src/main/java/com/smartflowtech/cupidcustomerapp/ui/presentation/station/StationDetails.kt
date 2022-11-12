package com.smartflowtech.cupidcustomerapp.ui.presentation.station

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.Station
import com.smartflowtech.cupidcustomerapp.model.response.VendorStation
import com.smartflowtech.cupidcustomerapp.ui.theme.AthleticsFontFamily
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.grey
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeEachWord
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@Composable
fun StationDetails(station: VendorStation) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_station_location),
                contentDescription = "",
                tint = Color.Unspecified,
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = station.name?.capitalizeEachWord() ?: "",
            color = darkBlue,
            fontFamily = AthleticsFontFamily,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        station.address?.capitalizeEachWord()?.let {
            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = it,
                color = grey,
                fontFamily = AthleticsFontFamily,
            )
        }

        station.managerPhone?.let {
            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = it,
                color = darkBlue,
                fontFamily = AthleticsFontFamily,
            )
        }

        station.managerEmail?.let {
            Spacer(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth()
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = it,
                color = darkBlue,
                fontFamily = AthleticsFontFamily,
            )
        }

    }

}

@Composable
@Preview(showBackground = true)
fun PreviewStationDetails() {
    StationDetails(
        station = VendorStation(
            1,
            "Ardova Gas Station",
            "",
            "10 Koka Road, Iyana Ipaja",
            "",
            "",
            "",
            "ardova@gmail.com",
            "Ikeja",
            "Lagos",
            1,
            1,
            1,
            "",
            ""
        )
    )
}