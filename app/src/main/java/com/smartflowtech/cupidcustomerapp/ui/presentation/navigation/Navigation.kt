package com.smartflowtech.cupidcustomerapp.ui.presentation.navigation

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.SnapSpec
import androidx.compose.animation.core.snap
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Home
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.HomeScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Location
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.Settings
import com.smartflowtech.cupidcustomerapp.ui.presentation.login.LoginScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.onboarding.GetStartedFirstScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.onboarding.GetStartedSecondScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.splash.SplashScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.Transactions

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CupidCustomerAppNavigation(navHostController: NavHostController) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = Screen.Splash.route,
        enterTransition = {
            slideInHorizontally { it }
        },
        exitTransition = {
            slideOutHorizontally { -it }
        },
        popEnterTransition = {
            slideInHorizontally { -it }
        },
        popExitTransition = {
            slideOutHorizontally { it }
        }
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
            val bottomNavHostController = rememberAnimatedNavController()
            val currentRoute =
                bottomNavHostController.currentBackStackEntryAsState().value?.destination?.route
            HomeScreen(
                navHostController = bottomNavHostController,
                goTo = {},
                isNavDestinationSelected = { route ->
                    currentRoute == route
                },
                onBackPressed = {
                    Log.d("Pri", "Pressed")
                    bottomNavHostController.navigate(Screen.Home.route)
                },
                onBottomNavItemClicked = { route ->
                    Log.d("Pri", "Pressed")
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
                }
            )
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreenBottomNavBarNavigation(bottomNavHostController: NavHostController) {
    AnimatedNavHost(bottomNavHostController,
        startDestination = HomeScreen.Home.route,
        enterTransition = {
            if (targetState.destination.route == HomeScreen.Transactions.route) {
                slideInVertically { it }
            } else {
                slideInHorizontally { it }
            }
        },
        exitTransition = {
            if (initialState.destination.route == HomeScreen.Home.route ||
                targetState.destination.route == HomeScreen.Transactions.route
            ) {
                fadeOut(animationSpec = snap())
            } else {
                slideOutHorizontally { -it }
            }
        },
        popEnterTransition = {
            slideInHorizontally { -it }
        },
        popExitTransition = {
            slideOutHorizontally { it }
        }
    ) {
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