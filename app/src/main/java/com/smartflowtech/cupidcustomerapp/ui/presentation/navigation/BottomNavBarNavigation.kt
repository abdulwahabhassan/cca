package com.smartflowtech.cupidcustomerapp.ui.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.snap
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Home
import com.smartflowtech.cupidcustomerapp.ui.presentation.profile.Profile
import com.smartflowtech.cupidcustomerapp.ui.presentation.settings.Settings
import com.smartflowtech.cupidcustomerapp.ui.presentation.location.Location
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.HomeScreenUiState
import com.smartflowtech.cupidcustomerapp.ui.presentation.notification_settings.NotificationSettings
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.ChangePasswordScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.payment.PaymentSettings
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.Transactions
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.ChangePasswordViewModel
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.ProfileViewModel
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.SettingsViewModel


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
    isCardSelected: Boolean,
    onUploadImageClicked: () -> Unit,
    onProfileUpdateSuccess: () -> Unit,
    profilePicture: String,
    onLogOutClicked: () -> Unit,
    userFullName: String,
    userName: String
) {

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
            } else if ((initialState.destination.route == HomeScreen.Transactions.route ||
                        initialState.destination.route == HomeScreen.Profile.route) &&
                targetState.destination.route == HomeScreen.Home.route
            ) {
                slideOutVertically { -it }
            } else {
                slideOutHorizontally { -it }
            }
        },
        popEnterTransition = {
            if (targetState.destination.route == HomeScreen.Home.route) {
                slideInVertically { -it }
            }
            else {
                slideInHorizontally { -it }
            }
        },
        popExitTransition = {
            if ((initialState.destination.route == HomeScreen.Transactions.route ||
                        initialState.destination.route == HomeScreen.Profile.route) &&
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
                onBackPressed = onBackPressed,
                homeScreenUiState = homeScreenUiState,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                getTransactions = getTransactions,
                isCardSelected = isCardSelected,
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                },
                currentBottomNavDestination = bottomNavHostController.currentDestination?.route
                    ?: ""
            )
        }
        composable(HomeScreen.Transactions.route) {
            Transactions(
                onDownloadTransactionsClicked = onDownloadTransactionsClicked,
                onSearchBarClicked = onSearchBarClicked,
                onBackPressed = onBackPressed,
                homeScreenUiState = homeScreenUiState,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                isCardSelected = isCardSelected,
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                },
                currentBottomNavDestination = bottomNavHostController.currentDestination?.route
                    ?: ""
            )
        }
        composable(HomeScreen.Location.route) {
            Location()
        }
        composable(HomeScreen.Settings.route) {
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            Settings(
                viewModel = settingsViewModel,
                onLogOutClicked = onLogOutClicked,
                onEditProfileClicked = {
                    bottomNavHostController.navigate(
                        route = HomeScreen.Profile.route,
                        navOptions = navOptions { launchSingleTop = true })
                },
                onSecurityClicked = {
                    bottomNavHostController.navigate(
                        route = HomeScreen.Security.route,
                        navOptions = navOptions { launchSingleTop = true })
                },
                onNotificationClicked = {
                    bottomNavHostController.navigate(
                        route = HomeScreen.Notification.route,
                        navOptions = navOptions { launchSingleTop = true })
                },
                onPaymentClicked = {
                    bottomNavHostController.navigate(
                        route = HomeScreen.Payment.route,
                        navOptions = navOptions { launchSingleTop = true })
                },
                onBackPressed = onBackPressed
            )
        }
        composable(HomeScreen.Profile.route) {
            val profileViewModel = hiltViewModel<ProfileViewModel>()
            Profile(
                viewModel = profileViewModel,
                uiState = profileViewModel.profileScreenUiState,
                onUploadImageClicked = onUploadImageClicked,
                onProfileUpdateSuccess = onProfileUpdateSuccess,
                profilePicture = profilePicture,
                onBackPressed = onBackPressed,
                userFullName = userFullName,
                userName = userName
            )
        }
        composable(HomeScreen.Security.route) {
            val changePasswordViewModel = hiltViewModel<ChangePasswordViewModel>()
            ChangePasswordScreen(
                viewModel = changePasswordViewModel,
                uiState = changePasswordViewModel.changePasswordScreenUiState,
                onSuccessDialogOkayPressed = onBackPressed,
                isForgotPassWord = false,
                okayButtonText = "Okay"
            )
        }

        composable(HomeScreen.Notification.route) {
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            NotificationSettings(
                viewModel = settingsViewModel
            )
        }

        composable(HomeScreen.Payment.route) {
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            PaymentSettings(
//                viewModel = settingsViewModel
            )
        }
    }
}