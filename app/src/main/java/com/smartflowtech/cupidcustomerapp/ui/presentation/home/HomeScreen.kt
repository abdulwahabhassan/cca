package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.BottomNavBarNavigation
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    bottomNavBarNavHostController: NavHostController,
    goTo: () -> Unit,
    isNavDestinationSelected: (String) -> Boolean,
    onBackPressed: () -> Unit,
    onBottomNavItemClicked: (String) -> Unit,
    onFilteredClicked: () -> Unit,
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
    var sheetPeekHeight by remember {
        mutableStateOf(
            if (localConfig.screenWidthDp.dp > 320.dp)
                localConfig.screenHeightDp.dp * 0.50f
            else
                localConfig.screenHeightDp.dp * 0.40f
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
                currentBottomNavDestinationTitle =
                    HomeScreen.Home.title
                bottomAppBarVisibility = true
            }
            HomeScreen.Transactions.route -> {
                currentBottomNavDestinationTitle =
                    HomeScreen.Transactions.title
                bottomAppBarVisibility = true
            }
            HomeScreen.Location.route -> {
                currentBottomNavDestinationTitle =
                    HomeScreen.Location.title
                bottomAppBarVisibility = true
            }
            HomeScreen.Settings.route -> {
                currentBottomNavDestinationTitle =
                    HomeScreen.Settings.title
                bottomAppBarVisibility = true
            }
            HomeScreen.Profile.route -> {
                currentBottomNavDestinationTitle =
                    HomeScreen.Profile.title
                bottomAppBarVisibility = false
            }
            HomeScreen.Security.route -> {
                currentBottomNavDestinationTitle =
                    HomeScreen.Security.title
                bottomAppBarVisibility = false
            }
            HomeScreen.Notification.route -> {
                currentBottomNavDestinationTitle =
                    HomeScreen.Notification.title
                bottomAppBarVisibility = false
            }
            HomeScreen.Payment.route -> {
                currentBottomNavDestinationTitle =
                    HomeScreen.Payment.title
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
            sheetPeekHeight = if (localConfig.screenWidthDp.dp > 320.dp)
                localConfig.screenHeightDp.dp * 0.50f
            else
                localConfig.screenHeightDp.dp * 0.40f
        }
    })

    Scaffold(
        bottomBar = {
            HomeBottomAppBar(
                isSelected = isNavDestinationSelected,
                onClicked = onBottomNavItemClicked,
                visible = bottomAppBarVisibility
            )
        }
    ) { paddingValues ->

        BottomSheetScaffold(
            modifier = Modifier.padding(paddingValues),
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
                            text = if (
                                currentBottomNavDestinationTitle ==
                                HomeScreen.Transactions.title &&
                                isCardSelected
                            ) "Card History"
                            else
                                currentBottomNavDestinationTitle,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        if (
                            currentBottomNavDestinationTitle == HomeScreen.Transactions.title
                        ) {
                            IconButton(onClick = {
                                onFilteredClicked()
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
                    Spacer(modifier = Modifier.height(16.dp))
                    if (currentBottomNavDestinationTitle == HomeScreen.Home.title) {
                        Icon(
                            modifier = Modifier
                                .size(46.dp)
                                .padding(bottom = 16.dp)
                                .clip(RoundedCornerShape(50))
                                .clipToBounds()
                                .clickable {
                                    if (
                                        bottomNavBarNavHostController.currentDestination?.route ==
                                        HomeScreen.Home.route
                                    ) {
                                        onBottomNavItemClicked(HomeScreen.Transactions.route)
                                    } else {
                                        onBottomNavItemClicked(HomeScreen.Home.route)
                                    }
                                }
                                .align(Alignment.CenterHorizontally),
                            imageVector = if (bottomSheetState.isExpanded)
                                Icons.Rounded.HorizontalRule
                            else
                                Icons.Rounded.HorizontalRule,
                            contentDescription = "Bottom sheet handle",
                            tint = grey
                        )
                    } else {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                    BottomNavBarNavigation(
                        bottomNavHostController = bottomNavBarNavHostController,
                        onBackPressed = {
                            if (isCardSelected && bottomNavBarNavHostController.currentDestination
                                    ?.route == HomeScreen.Home.route
                            ) {
                                isCardSelected = false
                            } else {
                                onBackPressed()
                            }
                        },
                        onSearchBarClicked = {
                            coroutineScope.launch {
                                bottomSheetState.expand()
                            }
                        },
                        homeScreenUiState = homeScreenUiState,
                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                        getTransactions = getTransactions,
                        onDownloadTransactionsClicked = onDownloadTransactionsClicked,
                        isCardSelected,
                        onUploadImageClicked = onUploadImageClicked,
                        onProfileUpdateSuccess = onProfileUpdateSuccess,
                        profilePicture = profilePicture,
                        onLogOutClicked = onLogOutClicked,
                        userFullName = userFullName,
                        userName = userName
                    )
                }
            }) { paddingValues ->

            HomeDashBoard(
                bottomSheetState = bottomSheetState,
                onAddFundsClicked = onAddFundsClicked,
                fullName = userFullName,
                walletBalanceVisibility = walletBalanceVisibility,
                updateWalletVisibility = updateWalletVisibility,
                homeScreenUiState = homeScreenUiState,
                onCardSelected = { bool ->
                    isCardSelected = bool
                },
                isCardSelected = isCardSelected,
                onProfileClicked = {
                    onBottomNavItemClicked(HomeScreen.Profile.route)
                },
                profilePicture = profilePicture
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