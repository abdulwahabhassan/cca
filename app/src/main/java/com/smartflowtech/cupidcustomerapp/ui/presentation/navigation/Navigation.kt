package com.smartflowtech.cupidcustomerapp.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.*
import com.smartflowtech.cupidcustomerapp.ui.presentation.login.LoginScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.onboarding.GetStartedFirstScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.onboarding.GetStartedSecondScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.splash.SplashScreen

@Composable
fun CupidCustomerAppNavigation(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(goToHomeScreen = {
                navHostController.navigate(
                    Screen.Home.route,
                    NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                )
            }, goToGetStartedScreen = {
                navHostController.navigate(
                    Screen.GetStartedFirst.route,
                    NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                )
            }, goToLoginScreen = {
                navHostController.navigate(
                    Screen.Login.route,
                    NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                )
            })
        }
        composable(route = Screen.GetStartedFirst.route) {
            GetStartedFirstScreen {
                navHostController.navigate(
                    Screen.GetStartedSecond.route
                )
            }
        }
        composable(route = Screen.GetStartedSecond.route) {
            GetStartedSecondScreen {
                navHostController.navigate(
                    Screen.Login.route
                )
            }
        }
        composable(route = Screen.Login.route) {
            LoginScreen(goToHomeScreen = {
                navHostController.navigate(
                    Screen.Home.route
                )
            }, {})
        }

        composable(route = Screen.Home.route) {
            val bottomNavHostController = rememberNavController()
            val currentRoute =
                bottomNavHostController.currentBackStackEntryAsState().value?.destination?.route
            HomeScreen(
                navHostController = bottomNavHostController,
                goTo = {},
                isNavDestinationSelected = { route ->
                    currentRoute == route
                },
                onBottomNavItemClicked = { route ->
                    bottomNavHostController.navigate(route) {
                        bottomNavHostController.graph.startDestinationRoute
                            ?.let { startDestinationRoute ->
                                popUpTo(startDestinationRoute) {
                                    saveState = true
                                }
                            }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }

}

@Composable
fun HomeScreenBottomNavBarNavigation(bottomNavHostController: NavHostController) {
    NavHost(bottomNavHostController, startDestination = HomeScreen.Home.route) {
        composable(HomeScreen.Home.route) {
            Home(goToTransactions = {
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
            })
        }
        composable(HomeScreen.Transactions.route) {
            Transactions()
        }
        composable(HomeScreen.Location.route) {
            Location()
        }
        composable(HomeScreen.Settings.route) {
            Settings()
        }
    }
}