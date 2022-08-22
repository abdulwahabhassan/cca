package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.ui.theme.*
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreenBottomNavBarNavigation
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    goTo: () -> Unit,
    isNavDestinationSelected: (String) -> Boolean,
    onBackPressed: () -> Unit,
    onBottomNavItemClicked: (String) -> Unit,
) {

    val bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    val coroutineScope = rememberCoroutineScope()
    var currentBottomNavDestinationTitle by rememberSaveable { mutableStateOf(com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Home.title) }

    Scaffold(
        bottomBar = {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                HomeBottomAppBar(
                    isSelected = isNavDestinationSelected,
                    onClicked = onBottomNavItemClicked.also { route ->
                        when (navHostController.currentDestination?.route) {
                            com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Home.route -> {
                                currentBottomNavDestinationTitle = com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Home.title
                            }
                            com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Transactions.route -> {
                                currentBottomNavDestinationTitle = com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Transactions.title
                            }
                            com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Location.route -> {
                                currentBottomNavDestinationTitle = com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Location.title
                            }
                            com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Settings.route -> {
                                currentBottomNavDestinationTitle = com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Settings.title
                            }
                        }
                        coroutineScope.launch {
                            if (navHostController.currentDestination?.route == com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen.Home.route) {
                                bottomSheetScaffoldState.bottomSheetState.collapse()
                            } else {
                                bottomSheetScaffoldState.bottomSheetState.expand()
                            }
                        }
                    }
                )
            }
        }
    ) { paddingValues: PaddingValues ->

        BottomSheetScaffold(
            modifier = Modifier.padding(paddingValues = paddingValues),
            scaffoldState = bottomSheetScaffoldState,
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent,
            sheetPeekHeight = LocalConfiguration.current.screenHeightDp.dp * 0.60f,
            sheetContent = {
                Spacer(
                    modifier = Modifier
                        .windowInsetsTopHeight(
                            WindowInsets.statusBars
                                .add(WindowInsets.statusBars)
                                .add(WindowInsets.statusBars)
                        )
                        .fillMaxWidth()
                )
                Column(
                    Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .width(40.dp)
                            .padding(bottom = 24.dp),
                        painter = painterResource(id = R.drawable.ic_bottom_sheet_handle_inactive),
                        contentDescription = "Bottom sheet handle",
                        tint = Color.Unspecified
                    )
                    HomeScreenBottomNavBarNavigation(bottomNavHostController = navHostController)
                }
            }) { paddingValues ->

            HomeDashBoard(bottomSheetState = bottomSheetState, currentBottomNavDestinationTitle, onBackPressed)

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