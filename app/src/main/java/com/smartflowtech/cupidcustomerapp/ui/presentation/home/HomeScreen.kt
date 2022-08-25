package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.HomeScreenViewModel
import com.smartflowtech.cupidcustomerapp.ui.theme.CupidCustomerAppTheme
import com.smartflowtech.cupidcustomerapp.ui.theme.NoRippleTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel,
    bottomNavBarNavHostController: NavHostController,
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
    var currentBottomNavDestinationTitle by rememberSaveable { mutableStateOf(HomeScreen.Home.title) }

    var visible by rememberSaveable { mutableStateOf(true) }
    visible =
        bottomSheetState.direction >= 0f && bottomSheetState.currentValue == BottomSheetValue.Collapsed

    LaunchedEffect(key1 = bottomSheetState.direction) {
        if (bottomSheetState.direction == -1f && currentBottomNavDestinationTitle == HomeScreen.Home.title) {
            onBottomNavItemClicked(HomeScreen.Transactions.route)
        }
    }

    LaunchedEffect(key1 = bottomNavBarNavHostController.currentDestination?.route, block = {
        coroutineScope.launch {
            if (bottomNavBarNavHostController.currentDestination?.route == HomeScreen.Home.route) {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            } else {
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        }

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
    ) { paddingValues: PaddingValues ->

        BottomSheetScaffold(
            modifier = Modifier.padding(paddingValues = paddingValues),
            scaffoldState = bottomSheetScaffoldState,
            sheetElevation = 0.dp,
            sheetBackgroundColor = Color.Transparent,
            sheetPeekHeight = LocalConfiguration.current.screenHeightDp.dp * 0.50f,
            sheetContent = {

                AnimatedVisibility(
                    visible = !visible,
                    enter = fadeIn(),
                    exit = fadeOut()
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

                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.FilterList,
                                        contentDescription = "Filter",
                                        tint = Color.White,
                                    )
                                }
                            } else {
                                IconButton(onClick = { }) {}
                            }
                        }

                    } else {
                        Spacer(
                            modifier = Modifier
                                .height(56.dp)
                        )
                    }
                }

                Column(
                    Modifier
                        .padding(top = 2.dp)
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
                    BottomNavBarNavigation(
                        bottomNavHostController = bottomNavBarNavHostController,
                        onBackPressed = onBackPressed
                    )
                }
            }) { paddingValues ->

            HomeDashBoard(
                horizontalPagerHeight = LocalConfiguration.current.screenHeightDp.dp * 0.30f,
                bottomSheetState = bottomSheetState,
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