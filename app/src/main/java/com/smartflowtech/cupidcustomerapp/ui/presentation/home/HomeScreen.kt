package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
import kotlin.math.absoluteValue


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
    getTransactions: () -> Unit,
    onDownloadTransactionsClicked: () -> Unit
) {

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val coroutineScope = rememberCoroutineScope()
    var currentBottomNavDestinationTitle by rememberSaveable { mutableStateOf(HomeScreen.Home.title) }

    val localConfig = LocalConfiguration.current

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
            sheetGesturesEnabled = false,
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
                    if (currentBottomNavDestinationTitle == HomeScreen.Transactions.title) {

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
                        bottomSheetScaffoldState = bottomSheetScaffoldState,
                        getTransactions = getTransactions,
                        onDownloadTransactionsClicked = onDownloadTransactionsClicked,
                        isCardSelected
                    )
                }
            }) { paddingValues ->

            HomeDashBoard(
                bottomSheetState = bottomSheetState,
                onAddFundsClicked = onAddFundsClicked,
                userName = userName,
                walletBalanceVisibility = walletBalanceVisibility,
                updateWalletVisibility = updateWalletVisibility,
                onLogOutClicked = onLogOutClicked,
                homeScreenUiState = homeScreenUiState,
                onCardSelected = { bool ->
                    isCardSelected = bool
                    if (bool) {
                        sheetPeekHeight *= 1.08f
                    } else {
                        sheetPeekHeight = if (localConfig.screenWidthDp.dp > 320.dp)
                            localConfig.screenHeightDp.dp * 0.50f
                        else
                            localConfig.screenHeightDp.dp * 0.40f
                    }

                },
                isCardSelected = isCardSelected
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