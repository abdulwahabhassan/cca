package com.smartflowtech.cupidcustomerapp.ui.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.snap
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Home
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Location
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Settings
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.HomeScreenUiState
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.Transactions


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun BottomNavBarNavigation(
    bottomNavHostController: NavHostController,
    onBackPressed: () -> Unit,
    onSearchBarClicked: () -> Unit,
    homeScreenUiState: HomeScreenUiState,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    getTransactions: () -> Unit,
    onDownloadTransactionsClicked: () -> Unit,
    isCardSelected: Boolean
) {

//    var selectedTransaction: Transaction? by remember {
//        mutableStateOf(
//            null
//        )
//    }
    var selectedTab by remember { mutableStateOf("Transactions") }

    AnimatedNavHost(bottomNavHostController,
        startDestination = HomeScreen.Home.route,
        enterTransition = {
            if (targetState.destination.route == HomeScreen.Transactions.route ||
                initialState.destination.route == HomeScreen.Home.route
            ) {
                slideInVertically { it }
            } else if (targetState.destination.route == HomeScreen.Home.route) {
                slideInVertically { -it }
            } else {
                slideInHorizontally { it }
            }
        },
        exitTransition = {
            if (initialState.destination.route == HomeScreen.Home.route ||
                targetState.destination.route == HomeScreen.Transactions.route
            ) {
                fadeOut(animationSpec = snap())
            } else if (initialState.destination.route == HomeScreen.Transactions.route &&
                targetState.destination.route == HomeScreen.Home.route
            ) {
                slideOutVertically { -it }
            } else if (initialState.destination.route == HomeScreen.Transactions.route &&
                targetState.destination.route == HomeScreen.Home.route
            ) {
                fadeOut(snap())
            } else {
                slideOutHorizontally { -it }
            }
        },
        popEnterTransition = {
            if (targetState.destination.route == HomeScreen.Home.route) {
                slideInVertically { -it }
            } else {
                slideInHorizontally { -it }
            }
        },
        popExitTransition = {
            if (initialState.destination.route == HomeScreen.Transactions.route &&
                targetState.destination.route == HomeScreen.Home.route
            ) {
                fadeOut(snap())
            } else {
                slideOutHorizontally { it }
            }
        }
    ) {
        composable(HomeScreen.Home.route) {
            Home(
                goToTransactions = {
                    bottomNavHostController.navigate(HomeScreen.Transactions.route) {
                        bottomNavHostController.graph.startDestinationRoute
                            ?.let { startDestinationRoute ->
                                popUpTo(startDestinationRoute) {
                                    saveState = true
                                }
                            }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                onBackPressed = onBackPressed
//                    .also {
//                    selectedTransaction = null
//                }
                ,
                homeScreenUiState = homeScreenUiState,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                getTransactions = getTransactions,
                isCardSelected = isCardSelected,
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                }
            )
        }
        composable(HomeScreen.Transactions.route) {
            Transactions(
                onDownloadTransactionsClicked = onDownloadTransactionsClicked,
                onSearchBarClicked = onSearchBarClicked,
                onBackPressed = onBackPressed
//                    .also {
//                    selectedTransaction = null
//                }
                ,
                homeScreenUiState = homeScreenUiState,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                isCardSelected = isCardSelected,
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                }
            )
        }
        composable(HomeScreen.Location.route) {
            Location()
        }
        composable(HomeScreen.Settings.route) {
            Settings()
        }
    }
}