package com.smartflowtech.cupidcustomerapp.ui.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.NewPasswordScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.ResetPasswordScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.VerifyEmailScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.splash.SplashScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(
    finishActivity: () -> Unit
) {

    val rootNavHostController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = rootNavHostController,
        startDestination = Screen.Splash.route,
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
            val splashViewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                onboarded = splashViewModel.appConfigPreferences.onBoarded,
                loggedIn = splashViewModel.appConfigPreferences.loggedIn,
                goToHomeScreen = {
                    rootNavHostController.navigate(
                        Screen.Login.route,
                        NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                    )
                    rootNavHostController.navigate(
                        Screen.Home.route
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
                viewModel = loginViewModel,
                uiState = loginViewModel.loginScreenUiState,
                goToHomeScreen = {
                    rootNavHostController.navigate(
                        Screen.Login.route,
                        NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                    )
                    rootNavHostController.navigate(
                        Screen.Home.route,
                    )
                },
                goToResetPassword = {
                    rootNavHostController.navigate(
                        Screen.ResetPassword.route
                    )
                },
                finishActivity = {
                    finishActivity()
                }
            )

        }

        composable(route = Screen.ResetPassword.route) {
            val resetPasswordViewModel = hiltViewModel<ResetPasswordViewModel>()
            ResetPasswordScreen(
                viewModel = resetPasswordViewModel,
                uiState = resetPasswordViewModel.resetPasswordScreenUiState,
                goToVerifyEmailScreen = {
                    rootNavHostController.navigate(
                        Screen.VerifyEmail.route
                    )
                },
                goToLoginScreen = {
                    rootNavHostController.navigate(
                        Screen.Login.route
                    )
                },
            )
        }

        composable(route = Screen.VerifyEmail.route) {
            VerifyEmailScreen(
                goToNewPasswordScreen = {
                    rootNavHostController.navigate(
                        Screen.NewPassword.route
                    )
                },
                goToResetPasswordScreen = {
                    rootNavHostController.navigate(
                        Screen.ResetPassword.route
                    )
                }
            )
        }

        composable(route = Screen.NewPassword.route) {
            val newPasswordViewModel = hiltViewModel<NewPasswordViewModel>()
            NewPasswordScreen(
                viewModel = newPasswordViewModel,
                uiState = newPasswordViewModel.newPasswordScreenUiState,
                goToLoginScreen = {
                    rootNavHostController.navigate(
                        Screen.Login.route
                    )
                },
                goToResetPasswordScreen = {
                    rootNavHostController.navigate(
                        Screen.ResetPassword.route
                    )
                }
            )
        }


        composable(route = Screen.Home.route) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            val bottomNavBarNavHostController = rememberAnimatedNavController()
            val currentRoute =
                bottomNavBarNavHostController.currentBackStackEntryAsState().value?.destination?.route
            HomeScreenModalBottomSheetLayer(
                viewModel = homeViewModel,
                bottomNavBarNavHostController = bottomNavBarNavHostController,
                goToLogin = {
                    rootNavHostController.navigate(
                        Screen.Login.route,
                        NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                    )
                },
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
            val addFundsViewModel = hiltViewModel<AddFundsViewModel>()
            AddFundsScreenModalBottomSheetLayer(
                viewModel = addFundsViewModel,
                goBackToHomeScreen = {
                    rootNavHostController.popBackStack()
                }
            )

        }

    }


}

