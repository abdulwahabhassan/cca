package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.snap
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.BottomNavBarNavigation
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.HomeScreenUiState
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
    userName: String,
    walletBalanceVisibility: Boolean,
    updateWalletVisibility: (Boolean) -> Unit,
    homeScreenUiState: HomeScreenUiState,
    onLogOutClicked: () -> Unit,
    getTransactions: () -> Unit
) {

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val coroutineScope = rememberCoroutineScope()
    var currentBottomNavDestinationTitle by rememberSaveable { mutableStateOf(HomeScreen.Home.title) }

    val visible by remember {
        derivedStateOf { bottomSheetState.targetValue == BottomSheetValue.Expanded }
    }

    LaunchedEffect(key1 = bottomSheetState.targetValue) {
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
            }
            HomeScreen.Transactions.route -> {
                currentBottomNavDestinationTitle =
                    HomeScreen.Transactions.title
            }
            HomeScreen.Location.route -> {
                currentBottomNavDestinationTitle =
                    HomeScreen.Location.title
            }
            HomeScreen.Settings.route -> {
                currentBottomNavDestinationTitle =
                    HomeScreen.Settings.title
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

    Scaffold(
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                HomeBottomAppBar(
                    isSelected = isNavDestinationSelected,
                    onClicked = onBottomNavItemClicked
                )
            }
        }
    ) { paddingValues ->

        BottomSheetScaffold(
            modifier = Modifier.padding(paddingValues),
            scaffoldState = bottomSheetScaffoldState,
            sheetElevation = 0.dp,
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
            sheetPeekHeight = if (LocalConfiguration.current.screenWidthDp.dp > 320.dp)
                LocalConfiguration.current.screenHeightDp.dp * 0.50f
            else
                LocalConfiguration.current.screenHeightDp.dp * 0.40f,
            sheetContent = {

                AnimatedVisibility(
                    modifier = Modifier.alpha(bottomSheetState.progress.fraction),
                    visible = visible,
                    enter = fadeIn(snap()),
                    exit = fadeOut(snap())
                ) {

                    if (currentBottomNavDestinationTitle != HomeScreen.Home.title) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 32.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
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
                                text = currentBottomNavDestinationTitle,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            if (currentBottomNavDestinationTitle == HomeScreen.Transactions.title) {
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
                                IconButton(onClick = {}) {}
                            }
                        }

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
                    Icon(
                        modifier = Modifier
                            .padding(bottom = 24.dp)
                            .clip(RoundedCornerShape(50))
                            .clipToBounds()
                            .clickable {
                                if (bottomNavBarNavHostController.currentDestination?.route == HomeScreen.Home.route) {
                                    onBottomNavItemClicked(HomeScreen.Transactions.route)
                                } else {
                                    onBottomNavItemClicked(HomeScreen.Home.route)
                                }
                            }
                            .align(Alignment.CenterHorizontally)
                            .width(40.dp),
                        painter = painterResource(id = R.drawable.ic_bottom_sheet_handle_inactive),
                        contentDescription = "Bottom sheet handle",
                        tint = Color.Unspecified
                    )
                    BottomNavBarNavigation(
                        bottomNavHostController = bottomNavBarNavHostController,
                        onBackPressed = onBackPressed,
                        onSearchBarClicked = {
                            coroutineScope.launch {
                                bottomSheetState.expand()
                            }
                        },
                        homeScreenUiState = homeScreenUiState,
//                        bottomSheetState = bottomSheetState,
                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                        getTransactions = getTransactions
                    )
                }
            }) { paddingValues ->

            HomeDashBoard(
                horizontalPagerHeight = LocalConfiguration.current.screenHeightDp.dp * 0.30f,
                bottomSheetState = bottomSheetState,
                onAddFundsClicked = onAddFundsClicked,
                userName = userName,
                walletBalanceVisibility = walletBalanceVisibility,
                updateWalletVisibility = updateWalletVisibility,
                onLogOutClicked = onLogOutClicked,
                homeScreenUiState = homeScreenUiState
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