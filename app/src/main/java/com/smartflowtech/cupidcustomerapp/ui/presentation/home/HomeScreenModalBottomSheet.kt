package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.domain.AppConfigPreferences
import com.smartflowtech.cupidcustomerapp.model.domain.Station
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.DaysFilter
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.Success
import com.smartflowtech.cupidcustomerapp.ui.presentation.location.StationDetails
import com.smartflowtech.cupidcustomerapp.ui.presentation.location.StationFilter
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreenModalBottomSheetContent
import com.smartflowtech.cupidcustomerapp.ui.presentation.profile.UploadImage
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.DownloadTransactions
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.FilterTransactions

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenModalBottomSheet(
    modalBottomSheetState: ModalBottomSheetState,
    bottomNavBarNavHostController: NavHostController,
    isNavDestinationSelected: (String) -> Boolean,
    goBack: () -> Unit,
    onBottomNavItemClicked: (String) -> Unit,
    onAddFundsClicked: () -> Unit,
    updateWalletVisibility: (Boolean) -> Unit,
    appConfigPreferences: AppConfigPreferences,
    onSaveFilterClicked: (String, Map<String, Boolean>) -> Unit,
    homeScreenUiState: HomeScreenUiState,
    onLogoutClicked: () -> Unit,
    getTransactions: () -> Unit,
    persistProfilePicture: (String) -> Unit,
    profilePicture: String,
    onStationFilterSelected: (String) -> Unit,
    modalBottomSheetContentKey: String,
    setModalBottomSheetContent: (String) -> Unit,
    onDaysFilterSelected: (String) -> Unit
) {

    var successTitle: String by rememberSaveable { mutableStateOf("Success") }
    var successMessage: String by rememberSaveable { mutableStateOf("") }
    var station: Station? by remember { mutableStateOf(null) }

    ModalBottomSheetLayout(
        modifier = Modifier.navigationBarsPadding(),
        sheetState = modalBottomSheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContentColor = Color.Transparent,
        sheetContent = {

            Column {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenHeightDp.dp * 0.12f)
                )

                Column(
                    Modifier
                        .padding(top = 2.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .padding(end = 8.dp),
                            enabled = false
                        ) {}

                        Icon(
                            modifier = Modifier
                                .width(40.dp),
                            painter = painterResource(id = R.drawable.ic_bottom_sheet_handle_inactive),
                            contentDescription = "Bottom sheet handle",
                            tint = Color.Unspecified
                        )

                        //Close button
                        IconButton(
                            onClick = goBack,
                            modifier = Modifier
                                .padding(end = 8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close icon",
                                tint = Color.Black
                            )
                        }
                    }
                    when (modalBottomSheetContentKey) {
                        HomeScreenModalBottomSheetContent.DownloadTransactions.contentKey -> {
                            DownloadTransactions(
                                showSuccess = {
                                    successTitle = "Sent"
                                    successMessage =
                                        "We've sent the requested statements to your email"
                                    setModalBottomSheetContent(
                                        HomeScreenModalBottomSheetContent.Success.contentKey
                                    )
                                }
                            )
                        }
                        HomeScreenModalBottomSheetContent.Success.contentKey -> {
                            Success(
                                title = successTitle,
                                message = successMessage,
                                onOkayPressed = goBack
                            )
                        }
                        HomeScreenModalBottomSheetContent.UploadImage.contentKey -> {
                            UploadImage(
                                onImageSelected = { uri ->
                                    persistProfilePicture(uri)
                                    goBack()
                                }
                            )
                        }
                        HomeScreenModalBottomSheetContent.StationsFilter.contentKey -> {
                            StationFilter(onStationFilterSelected = { filter ->
                                goBack()
                                onStationFilterSelected(filter)
                            })
                        }
                        HomeScreenModalBottomSheetContent.StationDetails.contentKey -> {
                            station?.let { StationDetails(station = it) } ?: Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "Select a station to see details",
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                        HomeScreenModalBottomSheetContent.TransactionsFilter.contentKey -> {
                            FilterTransactions(
                                appConfigPreferences = appConfigPreferences,
                                onFilterSaveClicked = onSaveFilterClicked,
                                onBackPressed = goBack
                            )
                        }
                        HomeScreenModalBottomSheetContent.DaysFilter.contentKey -> {
                            DaysFilter(onDaysFilterSelected = { filter ->
                                goBack()
                                onDaysFilterSelected(filter)
                            })
                        }
                    }
                }
            }
        }
    ) {
        HomeScreen(
            modalBottomSheetState = modalBottomSheetState,
            bottomNavBarNavHostController = bottomNavBarNavHostController,
            goTo = {},
            isNavDestinationSelected = isNavDestinationSelected,
            onBackPressed = goBack,
            onBottomNavItemClicked = onBottomNavItemClicked,
            onTransactionsFilterClicked = {
                setModalBottomSheetContent(
                    HomeScreenModalBottomSheetContent.TransactionsFilter.contentKey
                )
            },
            onNotificationsFilterClicked = {
                setModalBottomSheetContent(
                    HomeScreenModalBottomSheetContent.DaysFilter.contentKey
                )
            },
            onAddFundsClicked = onAddFundsClicked,
            userFullName = appConfigPreferences.fullName,
            userName = appConfigPreferences.userName,
            walletBalanceVisibility = appConfigPreferences.walletBalanceVisibility,
            updateWalletVisibility = updateWalletVisibility,
            homeScreenUiState = homeScreenUiState,
            onLogOutClicked = onLogoutClicked,
            getTransactions = getTransactions,
            onDownloadTransactionsClicked = {
                setModalBottomSheetContent(
                    HomeScreenModalBottomSheetContent.DownloadTransactions.contentKey
                )
            },
            onUploadImageClicked = {
                setModalBottomSheetContent(
                    HomeScreenModalBottomSheetContent.UploadImage.contentKey
                )
            },
            onProfileUpdateSuccess = {
                successTitle = "Successful"
                successMessage = "Profile updated"
                setModalBottomSheetContent(
                    HomeScreenModalBottomSheetContent.Success.contentKey
                )
                goBack()

            },
            profilePicture = profilePicture,
            onStationFilterClicked = {
                setModalBottomSheetContent(
                    HomeScreenModalBottomSheetContent.StationsFilter.contentKey
                )
            },
            onStationSelected = {
                station = it
                setModalBottomSheetContent(
                    HomeScreenModalBottomSheetContent.StationDetails.contentKey
                )
            },
            onGraphFilterClicked = {
                setModalBottomSheetContent(
                    HomeScreenModalBottomSheetContent.DaysFilter.contentKey
                )
            }
        )
    }
}
