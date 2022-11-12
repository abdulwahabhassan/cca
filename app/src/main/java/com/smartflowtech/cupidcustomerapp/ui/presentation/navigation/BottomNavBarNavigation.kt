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
import com.smartflowtech.cupidcustomerapp.model.domain.Station
import com.smartflowtech.cupidcustomerapp.model.response.VendorStation
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Home
import com.smartflowtech.cupidcustomerapp.ui.presentation.profile.Profile
import com.smartflowtech.cupidcustomerapp.ui.presentation.settings.Settings
import com.smartflowtech.cupidcustomerapp.ui.presentation.station.Stations
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.HomeScreenUiState
import com.smartflowtech.cupidcustomerapp.ui.presentation.notification.Notifications
import com.smartflowtech.cupidcustomerapp.ui.presentation.notification_settings.NotificationSettings
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.ChangePasswordScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.payment.PaymentSettings
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.Transactions
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.*


@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun BottomNavBarNavigation(
    bottomNavHostController: NavHostController,
    onBackPressed: () -> Unit,
    onSearchBarClicked: () -> Unit,
    homeScreenUiState: HomeScreenUiState,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    onDownloadTransactionsClicked: () -> Unit,
    isCardSelected: Boolean,
    onUploadImageClicked: () -> Unit,
    onProfileUpdateSuccess: () -> Unit,
    profilePicture: String,
    onLogOutClicked: () -> Unit,
    userFullName: String,
    userName: String,
    onBottomNavItemClicked: (String) -> Unit,
    onStationFilterClicked: () -> Unit,
    onStationSelected: (VendorStation) -> Unit,
    onGraphFilterClicked: () -> Unit
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
                        initialState.destination.route == HomeScreen.Profile.route ||
                        initialState.destination.route == HomeScreen.Notifications.route ||
                        initialState.destination.route == HomeScreen.Stations.route
                        ) &&
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
            } else {
                slideInHorizontally { -it }
            }
        },
        popExitTransition = {
            if ((initialState.destination.route == HomeScreen.Transactions.route ||
                        initialState.destination.route == HomeScreen.Profile.route ||
                        initialState.destination.route == HomeScreen.Notifications.route ||
                        initialState.destination.route == HomeScreen.Stations.route) &&
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
                isCardSelected = isCardSelected,
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                },
                currentBottomNavDestination = bottomNavHostController.currentDestination?.route
                    ?: "",
                bottomNavBarNavHostController = bottomNavHostController,
                bottomSheetState = bottomSheetScaffoldState.bottomSheetState,
                onBottomNavItemClicked = onBottomNavItemClicked,
                onGraphFilterClicked = onGraphFilterClicked
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
                    ?: "",
                onGraphFilterClicked = onGraphFilterClicked
            )
        }
        composable(HomeScreen.Stations.route) {
            hiltViewModel<StationsViewModel>().apply {
                Stations(
                    onStationFilterClicked = onStationFilterClicked,
                    stationFilter = appConfigPreferences.stationFilter,
                    onStationSelected = onStationSelected,
                    onBackPressed = onBackPressed,
                    uiState = stationsScreenUiState,
                    bottomSheetScaffoldState = bottomSheetScaffoldState,
                )
            }

        }
        composable(HomeScreen.Settings.route) {
            hiltViewModel<SettingsViewModel>().apply {
                Settings(
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
                            route = HomeScreen.NotificationSettings.route,
                            navOptions = navOptions { launchSingleTop = true })
                    },
                    onPaymentClicked = {
                        bottomNavHostController.navigate(
                            route = HomeScreen.PaymentSettings.route,
                            navOptions = navOptions { launchSingleTop = true })
                    },
                    onBackPressed = onBackPressed,
                    logOut = {
                        logOut()
                        onLogOutClicked()
                    }
                )
            }

        }
        composable(HomeScreen.Profile.route) {
            hiltViewModel<ProfileViewModel>().apply {
                Profile(
                    onUploadImageClicked = onUploadImageClicked,
                    onProfileUpdateSuccess = onProfileUpdateSuccess,
                    profilePicture = profilePicture,
                    onBackPressed = onBackPressed,
                    userFullName = userFullName,
                    userName = userName,
                    updateProfile = { firstname, lastname, email ->
                        updateProfile(firstname, lastname, email)

                    }
                )
            }

        }
        composable(HomeScreen.Security.route) {
            hiltViewModel<ChangePasswordViewModel>().apply {
                ChangePasswordScreen(
                    onSuccessDialogOkayPressed = onBackPressed,
                    isForgotPassWord = false,
                    changePassword = { currentPassword, newPassword ->
                        changePassword(currentPassword, newPassword)
                    }
                )
            }

        }

        composable(HomeScreen.NotificationSettings.route) {
            hiltViewModel<SettingsViewModel>().apply {
                NotificationSettings(
                    emailNotifications = appConfigPreferences.emailNotifications,
                    pushNotifications = appConfigPreferences.pushNotifications,
                    updateEmailNotification = { bool ->
                        updateEmailNotifications(bool)
                    },
                    updatePushNotification = { bool ->
                        updatePushNotifications(bool)
                    }
                )
            }

        }

        composable(HomeScreen.Notifications.route) {
            hiltViewModel<NotificationsViewModel>().apply {
                Notifications(
                    onBackPressed = onBackPressed,
                    uiState = notificationsScreenUiState
                )
            }

        }
        composable(HomeScreen.PaymentSettings.route) {
            hiltViewModel<SettingsViewModel>().apply {
                PaymentSettings(
                    paymentMethod = appConfigPreferences.paymentMethod,
                    updatePaymentMethod = { paymentMethod ->
                        updatePaymentMethod(paymentMethod)
                    }
                )
            }

        }
    }
}