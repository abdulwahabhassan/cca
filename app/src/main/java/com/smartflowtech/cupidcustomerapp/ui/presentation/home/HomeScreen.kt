package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.model.domain.CardHistoryPeriodFilterContext
import com.smartflowtech.cupidcustomerapp.model.response.VendorStation
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.BottomNavBarNavigation
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    modalBottomSheetState: ModalBottomSheetState,
    bottomNavBarNavHostController: NavHostController,
    goTo: () -> Unit,
    isNavDestinationSelected: (String) -> Boolean,
    onBackPressed: () -> Unit,
    onBottomNavItemClicked: (String) -> Unit,
    onTransactionsFilterClicked: () -> Unit,
    onNotificationsFilterClicked: (CardHistoryPeriodFilterContext) -> Unit,
    onAddFundsClicked: () -> Unit,
    userFullName: String,
    userName: String,
    walletBalanceVisibility: Boolean,
    updateWalletVisibility: (Boolean) -> Unit,
    homeScreenUiState: HomeScreenUiState,
    onLogOutClicked: () -> Unit,
    getTransactions: () -> Unit,
    onDownloadTransactionsClicked: () -> Unit,
    onUploadImageClicked: () -> Unit,
    onProfileUpdateSuccess: () -> Unit,
    profilePicture: String,
    onStationFilterClicked: () -> Unit,
    onStationSelected: (VendorStation) -> Unit,
    onGraphFilterClicked: (context: CardHistoryPeriodFilterContext, periods: List<String>) -> Unit,
    selectedMonthYearPeriod: String,
    cardHistoryPeriodFilterContext: CardHistoryPeriodFilterContext
) {

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val coroutineScope = rememberCoroutineScope()
    var currentBottomNavDestinationTitle by rememberSaveable { mutableStateOf(HomeScreen.Home.title) }

    val localConfig = LocalConfiguration.current
    var bottomAppBarVisibility by rememberSaveable { mutableStateOf(true) }

    var isCardSelected: Boolean by rememberSaveable { mutableStateOf(false) }
    var selectedCardNfcTagCode: String by rememberSaveable { mutableStateOf("") }

    var sheetPeekHeight by remember {
        mutableStateOf(
            if (localConfig.screenWidthDp.dp > 320.dp) localConfig.screenHeightDp.dp * 0.50f
            else localConfig.screenHeightDp.dp * 0.40f
        )
    }

    LaunchedEffect(key1 = bottomSheetState.currentValue) {
        if (bottomSheetState.targetValue == BottomSheetValue.Expanded &&
            currentBottomNavDestinationTitle == HomeScreen.Home.title
        ) {
            onBottomNavItemClicked(HomeScreen.Transactions.route)
        } else if (bottomSheetState.targetValue == BottomSheetValue.Collapsed &&
            currentBottomNavDestinationTitle != HomeScreen.Home.title
        ) {
            onBottomNavItemClicked(HomeScreen.Home.route)
        }
    }

    LaunchedEffect(key1 = bottomNavBarNavHostController.currentDestination?.route, block = {

        when (bottomNavBarNavHostController.currentDestination?.route) {
            HomeScreen.Home.route -> {
                currentBottomNavDestinationTitle = HomeScreen.Home.title
                bottomAppBarVisibility = true
            }
            HomeScreen.Transactions.route -> {
                currentBottomNavDestinationTitle = HomeScreen.Transactions.title
                bottomAppBarVisibility = true
            }
            HomeScreen.Stations.route -> {
                currentBottomNavDestinationTitle = HomeScreen.Stations.title
                bottomAppBarVisibility = true
            }
            HomeScreen.Settings.route -> {
                currentBottomNavDestinationTitle = HomeScreen.Settings.title
                bottomAppBarVisibility = true
            }
            HomeScreen.Profile.route -> {
                currentBottomNavDestinationTitle = HomeScreen.Profile.title
                bottomAppBarVisibility = false
            }
            HomeScreen.Security.route -> {
                currentBottomNavDestinationTitle = HomeScreen.Security.title
                bottomAppBarVisibility = false
            }
            HomeScreen.NotificationSettings.route -> {
                currentBottomNavDestinationTitle = HomeScreen.NotificationSettings.title
                bottomAppBarVisibility = false
            }
            HomeScreen.PaymentSettings.route -> {
                currentBottomNavDestinationTitle = HomeScreen.PaymentSettings.title
                bottomAppBarVisibility = false
            }
            HomeScreen.Notifications.route -> {
                currentBottomNavDestinationTitle = HomeScreen.Notifications.title
                bottomAppBarVisibility = false
            }
        }

        coroutineScope.launch {
            if (bottomNavBarNavHostController.currentDestination?.route == HomeScreen.Home.route) {
                bottomSheetScaffoldState.bottomSheetState.collapse()

            } else {
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        }


    })

    LaunchedEffect(key1 = isCardSelected, block = {
        if (isCardSelected) {
            sheetPeekHeight *= 1.08f
        } else {
            sheetPeekHeight =
                if (localConfig.screenWidthDp.dp > 320.dp) localConfig.screenHeightDp.dp * 0.50f
                else localConfig.screenHeightDp.dp * 0.40f
        }
    })

    Scaffold(bottomBar = {
        HomeBottomAppBar(
            isSelected = isNavDestinationSelected,
            onClicked = onBottomNavItemClicked,
            visible = bottomAppBarVisibility
        )
    }) { paddingValues ->

        BottomSheetScaffold(modifier = Modifier.padding(paddingValues),
            scaffoldState = bottomSheetScaffoldState,
            sheetElevation = 0.dp,
            sheetGesturesEnabled = true,
            snackbarHost = {
                SnackbarHost(it) { data ->
                    Snackbar(
                        backgroundColor = transparentPurple,
                        contentColor = purple,
                        snackbarData = data,
                        actionColor = darkBlue
                    )
                }
            },
            sheetBackgroundColor = Color.Transparent,
            sheetPeekHeight = if (currentBottomNavDestinationTitle == HomeScreen.Home.title)
                sheetPeekHeight * 1.2f
            else
                sheetPeekHeight,
            sheetContent = {
                Row(
                    modifier = Modifier
                        .alpha(if (bottomSheetState.isExpanded) 1f else 0f)
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 32.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (currentBottomNavDestinationTitle != HomeScreen.Home.title) {

                        IconButton(onClick = {
                            onBackPressed()
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = "Back arrow",
                                tint = Color.White,
                            )
                        }

                        Text(
                            text = if (currentBottomNavDestinationTitle ==
                                HomeScreen.Transactions.title && isCardSelected
                            ) "Card History"
                            else
                                currentBottomNavDestinationTitle,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        if ((currentBottomNavDestinationTitle == HomeScreen.Transactions.title ||
                                    currentBottomNavDestinationTitle ==
                                    HomeScreen.Notifications.title) && !isCardSelected
                        ) {
                            IconButton(onClick = {
                                if (currentBottomNavDestinationTitle ==
                                    HomeScreen.Transactions.title
                                ) {
                                    onTransactionsFilterClicked()
                                } else if (currentBottomNavDestinationTitle ==
                                    HomeScreen.Notifications.title
                                ) {
                                    onNotificationsFilterClicked(CardHistoryPeriodFilterContext.DEFAULT)
                                }

                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.FilterList,
                                    contentDescription = "Filter",
                                    tint = Color.White,
                                )
                            }
                        } else {
                            IconButton(enabled = false, onClick = {}) {}
                        }

                    } else {
                        IconButton(onClick = {}, enabled = false) {}
                    }
                }

                Column(
                    Modifier
                        .padding(top = 2.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .fillMaxSize()
                ) {
                    BottomNavBarNavigation(
                        bottomNavHostController = bottomNavBarNavHostController,
                        onBackPressed = {
                            if (modalBottomSheetState.isVisible) {
                                onBackPressed()
                            } else {
                                if (isCardSelected && bottomNavBarNavHostController.currentDestination?.route == HomeScreen.Home.route) {
                                    isCardSelected = false
                                    selectedCardNfcTagCode = ""
                                } else {
                                    onBackPressed()
                                }
                            }

                        },
                        onSearchBarClicked = {
                            coroutineScope.launch {
                                bottomSheetState.expand()
                            }
                        },
                        homeScreenUiState = homeScreenUiState,
                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                        onDownloadTransactionsClicked = onDownloadTransactionsClicked,
                        isCardSelected = isCardSelected,
                        selectedCardNfcTagCode = selectedCardNfcTagCode,
                        onUploadImageClicked = onUploadImageClicked,
                        onProfileUpdateSuccess = onProfileUpdateSuccess,
                        profilePicture = profilePicture,
                        onLogOutClicked = onLogOutClicked,
                        userFullName = userFullName,
                        userName = userName,
                        onBottomNavItemClicked = onBottomNavItemClicked,
                        onStationFilterClicked = onStationFilterClicked,
                        onStationSelected = onStationSelected,
                        onGraphFilterClicked = onGraphFilterClicked,
                        selectedMonthYearPeriod = selectedMonthYearPeriod,
                        cardHistoryPeriodFilterContext = cardHistoryPeriodFilterContext
                    )
                }
            }) { paddingValues ->

            HomeDashBoard(
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                onAddFundsClicked = onAddFundsClicked,
                fullName = userFullName,
                walletBalanceVisibility = walletBalanceVisibility,
                updateWalletVisibility = updateWalletVisibility,
                homeScreenUiState = homeScreenUiState,
                onCardSelected = { bool, nfcTagCode ->
                    selectedCardNfcTagCode = nfcTagCode
                    isCardSelected = bool
                },
                isCardSelected = isCardSelected,
                onProfileClicked = {
                    onBottomNavItemClicked(HomeScreen.Profile.route)
                },
                profilePicture = profilePicture,
                onNotificationsClicked = {
                    onBottomNavItemClicked(HomeScreen.Notifications.route)
                },
                getTransactions = getTransactions
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    CupidCustomerAppTheme {
        //HomeScreen({}, {}, { true }, {})
    }
}