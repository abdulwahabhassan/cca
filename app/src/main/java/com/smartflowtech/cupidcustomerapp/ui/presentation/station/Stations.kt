package com.smartflowtech.cupidcustomerapp.ui.presentation.station

import android.Manifest
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.Station
import com.smartflowtech.cupidcustomerapp.model.response.VendorStation
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.SearchBar
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.utils.Extension.capitalizeEachWord
import com.smartflowtech.cupidcustomerapp.ui.utils.Util
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun Stations(
    onStationFilterClicked: () -> Unit,
    stationFilter: String,
    onStationSelected: (VendorStation) -> Unit,
    onBackPressed: () -> Unit,
    uiState: StationsScreenUiState,
    bottomSheetScaffoldState: BottomSheetScaffoldState
) {

    BackHandler(true) {
        onBackPressed()
    }

    var selectedTab by remember { mutableStateOf("List") }

    var queryText by rememberSaveable { mutableStateOf("") }
    val stations by remember(queryText, uiState) {
        val list = uiState.data?.filter { station ->
            (station.name?.contains(queryText, true) == true)
        } ?: emptyList()
        mutableStateOf(list)
    }
    val coroutineScope = rememberCoroutineScope()

    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )


    fun showSnackBar(message: String) {
        coroutineScope.launch {
            bottomSheetScaffoldState.snackbarHostState.showSnackbar(
                message,
                duration = SnackbarDuration.Short
            )
        }
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .fillMaxSize()
    ) {

        // Show map
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(9.0820, 8.6753), 10f)
        }
        val uiSettings by remember { mutableStateOf(MapUiSettings()) }
        val properties by remember {
            mutableStateOf(MapProperties(mapType = MapType.NORMAL, isMyLocationEnabled = false))
        }

        if (selectedTab == "Map") {
            if (multiplePermissionsState.allPermissionsGranted) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    properties = properties,
                    uiSettings = uiSettings
                ) {
                    uiState.data?.filterNot { it.latitude == null || it.longitude == null }
                        ?.forEach { vendorStation ->
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        vendorStation.latitude?.toDouble()!!,
                                        vendorStation.longitude?.toDouble()!!
                                    )
                                ),
                                title = vendorStation.name,
                                onClick = {
                                    onStationSelected(vendorStation)
                                    true
                                }
                            )
                        }
                }
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        Util.getTextToShowGivenPermissions(
                            multiplePermissionsState.revokedPermissions,
                            multiplePermissionsState.shouldShowRationale
                        ),
                        color = darkBlue,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(
                                color = transparentBlue,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                        Text("Grant permission")
                    }
                }
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

            //List of stations
            if (selectedTab == "List") {
                when (uiState.viewModelResult) {
                    ViewModelResult.LOADING -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                strokeWidth = 2.dp, modifier = Modifier
                                    .height(54.dp)
                            )
                        }

                    }
                    ViewModelResult.ERROR -> {
                        if (uiState.message?.isNotEmpty() == true) {
                            showSnackBar(uiState.message)
                        }
                    }
                    ViewModelResult.SUCCESS -> {
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
                                                text = station.name?.capitalizeEachWord() ?: "",
                                                fontFamily = AthleticsFontFamily,
                                                fontWeight = FontWeight.W400,
                                                color = Color.Black
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = station.address?.capitalizeEachWord() ?: "",
                                                fontSize = 14.sp,
                                                color = grey
                                            )
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

    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun PreviewLocation() {
    CupidCustomerAppTheme {
        Stations(
            onStationFilterClicked = {},
            "state",
            onStationSelected = {},
            onBackPressed = {},
            uiState = StationsScreenUiState(ViewModelResult.LOADING),
            bottomSheetScaffoldState = BottomSheetScaffoldState(
                DrawerState(DrawerValue.Closed),
                BottomSheetState(BottomSheetValue.Collapsed),
                SnackbarHostState()
            )
        )
    }
}