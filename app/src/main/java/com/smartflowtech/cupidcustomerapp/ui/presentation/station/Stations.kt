package com.smartflowtech.cupidcustomerapp.ui.presentation.station

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.Station
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.SearchBar
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Util

@Composable
fun Stations(
    onStationFilterClicked: () -> Unit,
    stationFilter: String,
    onStationSelected: (Station) -> Unit,
    onBackPressed: () -> Unit
) {

    BackHandler(true) {
        onBackPressed()
    }

    var selectedTab by remember { mutableStateOf("List") }

    var queryText by rememberSaveable { mutableStateOf("") }
    val stations by remember(queryText) {
        val list = Util.getListOfStations().filter { station ->
            (station.name.contains(queryText, true))
        }
        mutableStateOf(list)
    }



    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .fillMaxSize()
    ) {

        // Show map
        val smartflow = LatLng(6.599, 3.372)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(smartflow, 10f)
        }
        val uiSettings by remember { mutableStateOf(MapUiSettings()) }
        val properties by remember {
            mutableStateOf(MapProperties(mapType = MapType.NORMAL))
        }

        if (selectedTab == "Map") {
            GoogleMap(
                modifier = Modifier
                    .fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = properties,
                uiSettings = uiSettings
            ) {
                Marker(
                    state = MarkerState(position = smartflow),
                    title = "Smartflow ",
                    snippet = "Marker in Singapore",
                    onClick = {
                        onStationSelected(
                            Station(
                                name = "Smartflow Technologies Limited",
                                address = "Plot E Ikosi Road, Oregun, Ikeja, Lagos",
                                contactEmail = "info@smartflowtech.com",
                                phoneNumber = "+234 81 2629 1902"
                            )
                        )
                        true
                    }
                )
            }
        }

        //Search bar and Filter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SearchBar(
                query = queryText,
                onQueryChange = { newText ->
                    queryText = newText

                },
                searchPlaceholder = "Search",
                applyBorder = true,
                maxWidthFraction = 0.85f
            )
            IconButton(
                onClick = {
                    onStationFilterClicked()
                },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .background(
                        brush = Brush.horizontalGradient(gradientBluePurple),
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Rounded.FilterList,
                    contentDescription = "Filter",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
        ) {

            //List and Map tab
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentWidth(unbounded = true)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(3.dp))
                        .clip(RoundedCornerShape(3.dp))
                        .clipToBounds()
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) {
                            selectedTab = "List"
                        }
                        .padding(top = 4.dp, start = 8.dp, end = 8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "List",
                        color = if (selectedTab == "List") darkBlue else grey,
                        fontFamily = AthleticsFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Divider(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .width(50.dp)
                            .height(2.dp)
                            .background(
                                color = if (selectedTab == "List") darkBlue else Color.Transparent,
                                shape = RoundedCornerShape(50)
                            ),
                        color = if (selectedTab == "List") darkBlue else Color.Transparent
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Column(
                    modifier = Modifier
                        .wrapContentWidth(unbounded = true)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(3.dp))
                        .clip(RoundedCornerShape(3.dp))
                        .clipToBounds()
                        .clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = null
                        ) {
                            selectedTab = "Map"
                        }
                        .padding(top = 4.dp, start = 8.dp, end = 8.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Map",
                        color = if (selectedTab == "Map") darkBlue else grey,
                        fontFamily = AthleticsFontFamily,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Divider(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .width(50.dp)
                            .height(2.dp)
                            .background(
                                color = if (selectedTab == "Map") darkBlue else Color.Transparent,
                                shape = RoundedCornerShape(50)
                            ),
                        color = if (selectedTab == "Map") darkBlue else Color.Transparent
                    )
                }

            }

            Divider(
                thickness = 0.5.dp,
                color = if (selectedTab == "Map") Color.Transparent else lineGrey
            )

            Spacer(modifier = Modifier.height(16.dp))

            //List
            if (selectedTab == "List") {
                // Show list of stations
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(stations) { station ->
                        Column(
                            modifier = Modifier
                                .clickable {
                                    onStationSelected(station)
                                }
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 16.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier.size(32.dp),
                                    painter = painterResource(id = R.drawable.ic_station),
                                    contentDescription = "Stations icon"
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = station.name, fontFamily = AthleticsFontFamily,
                                        fontWeight = FontWeight.W400,
                                        color = Color.Black
                                    )
                                    Text(text = station.address, fontSize = 14.sp, color = grey)
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Divider(
                                color = lineGrey,
                                thickness = 0.5.dp,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
            }
        }

    }

}

@Composable
@Preview(showBackground = true)
fun PreviewLocation() {
    CupidCustomerAppTheme {
        Stations(onStationFilterClicked = {}, "state", onStationSelected = {}, onBackPressed = {})
    }
}