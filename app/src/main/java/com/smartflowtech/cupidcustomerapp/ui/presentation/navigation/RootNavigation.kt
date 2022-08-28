package com.smartflowtech.cupidcustomerapp.ui.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds.AddFundsScreenModalBottomSheetLayer
import com.smartflowtech.cupidcustomerapp.ui.presentation.home.HomeScreenModalBottomSheetLayer
import com.smartflowtech.cupidcustomerapp.ui.presentation.login.LoginScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.onboarding.GetStartedFirstScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.onboarding.GetStartedSecondScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.splash.SplashScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(
    rootNavHostController: NavHostController,
    finishActivity: () -> Unit
) {

    AnimatedNavHost(
        navController = rootNavHostController,
        startDestination = Screen.Home.route,
        enterTransition = {
            if (targetState.destination.route != Screen.Home.route) {
                slideInHorizontally { it }
            } else {
                fadeIn()
            }
        },
        exitTransition = {
            if (targetState.destination.route != Screen.Home.route) {
                slideOutHorizontally { -it }
            } else {
                fadeOut()
            }

        },
        popEnterTransition = {
            if (targetState.destination.route != Screen.Home.route) {
                slideInHorizontally { -it }
            } else {
                fadeIn()
            }
        },
        popExitTransition = {
            if (targetState.destination.route != Screen.Home.route) {
                slideOutHorizontally { it }
            } else {
                fadeOut()
            }

        }
    ) {

        composable(route = Screen.Splash.route) {
            val splashScreenViewModel = hiltViewModel<SplashScreenViewModel>()
            SplashScreen(
                onboarded = splashScreenViewModel.appConfigPreferences.onBoarded,
                loggedIn = splashScreenViewModel.appConfigPreferences.loggedIn,
                goToHomeScreen = {
                    rootNavHostController.navigate(
                        Screen.Home.route,
                        NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                    )
                },
                goToGetStartedScreen = {
                    rootNavHostController.navigate(
                        Screen.GetStartedFirst.route,
                        NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                    )
                },
                goToLoginScreen = {
                    rootNavHostController.navigate(
                        Screen.Login.route,
                        NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                    )
                })
        }
        composable(route = Screen.GetStartedFirst.route) {
            GetStartedFirstScreen {
                rootNavHostController.navigate(
                    Screen.GetStartedSecond.route
                )
            }
        }
        composable(route = Screen.GetStartedSecond.route) {
            val getStartedViewModel = hiltViewModel<GetStartedViewModel>()
            GetStartedSecondScreen(
                goToLoginScreen = {
                    rootNavHostController.navigate(
                        Screen.Login.route
                    )
                },
                getStarted = {
                    getStartedViewModel.updateStarted(true)
                }
            )
        }
        composable(route = Screen.Login.route) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                login = { loginRequestBody ->
                    loginViewModel.login(loginRequestBody)
                },
                uiState = loginViewModel.loginScreenUiState,
                goToHomeScreen = {
                    rootNavHostController.navigate(
                        Screen.Home.route,
                        NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                    )
                },
                goToForgotPasswordScreen = {}
            )
        }

        composable(route = Screen.Home.route) {
            val homeScreenViewModel = hiltViewModel<HomeScreenViewModel>()
            val bottomNavBarNavHostController = rememberAnimatedNavController()
            val currentRoute =
                bottomNavBarNavHostController.currentBackStackEntryAsState().value?.destination?.route
            HomeScreenModalBottomSheetLayer(
                viewModel = homeScreenViewModel,
                bottomNavBarNavHostController = bottomNavBarNavHostController,
                goTo = {},
                isNavDestinationSelected = { route ->
                    currentRoute == route
                },
                popBackStackOrFinishActivity = {
                    if (currentRoute == HomeScreen.Home.route) {
                        finishActivity()
                    } else {
                        bottomNavBarNavHostController.popBackStack()
                    }
                },
                goToDestination = { route ->
                    bottomNavBarNavHostController.navigate(route) {
                        bottomNavBarNavHostController.graph.startDestinationRoute
                            ?.let { startDestinationRoute ->
                                popUpTo(startDestinationRoute) {
                                    saveState = true
                                }
                            }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                goToAddFundsScreen = {
                    rootNavHostController.navigate(
                        Screen.AddFunds.route
                    )
                }
            )

        }

        composable(route = Screen.AddFunds.route) {
            val addFundsScreenViewModel = hiltViewModel<AddFundsScreenViewModel>()
            AddFundsScreenModalBottomSheetLayer(
                viewModel = addFundsScreenViewModel,
                goBackToHomeScreen = {
                    rootNavHostController.popBackStack()
                }
            )

        }

    }


}

