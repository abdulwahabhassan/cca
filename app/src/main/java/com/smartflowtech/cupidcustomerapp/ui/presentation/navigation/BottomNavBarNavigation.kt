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
import androidx.navigation.navigation
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
import timber.log.Timber


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BottomNavBarNavigation(bottomNavHostController: NavHostController, onBackPressed: () -> Unit) {
    AnimatedNavHost(bottomNavHostController,
        startDestination = HomeScreen.Home.route,
        enterTransition = {
            if (targetState.destination.route == HomeScreen.Transactions.route ||
                initialState.destination.route == HomeScreen.Home.route
            ) {
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
            )
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