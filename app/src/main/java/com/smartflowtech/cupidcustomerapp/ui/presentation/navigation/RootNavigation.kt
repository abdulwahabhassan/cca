package com.smartflowtech.cupidcustomerapp.ui.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.ChangePasswordScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.ResetPasswordScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.password.VerifyEmailScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.splash.SplashScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.*
import timber.log.Timber

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootNavigation(
    finishActivity: () -> Unit
) {

    val rootNavHostController = rememberAnimatedNavController()
    var verifiedEmail: String by rememberSaveable { mutableStateOf("") }

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
            hiltViewModel<SplashViewModel>().apply {
                SplashScreen(
                    onboarded = appConfigPreferences.onBoarded,
                    loggedIn = appConfigPreferences.loggedIn,
                    goToHomeScreen = {
                        rootNavHostController.navigate(
                            Screen.Login.route,
                            NavOptions
                                .Builder()
                                .setLaunchSingleTop(true)
                                .setPopUpTo(Screen.Splash.route, true)
                                .build()
                        )
                        rootNavHostController.navigate(
                            Screen.Home.route,
                            NavOptions
                                .Builder()
                                .setLaunchSingleTop(true)
                                .build()
                        )
                    },
                    goToGetStartedScreen = {
                        rootNavHostController.navigate(
                            Screen.GetStartedFirst.route,
                            NavOptions.Builder()
                                .setLaunchSingleTop(true)
                                .setPopUpTo(Screen.Splash.route, true)
                                .build()
                        )
                    },
                    goToLoginScreen = {
                        rootNavHostController.navigate(
                            Screen.Login.route,
                            NavOptions
                                .Builder()
                                .setLaunchSingleTop(true)
                                .setPopUpTo(Screen.Splash.route, true)
                                .build()
                        )
                    })
            }

        }
        composable(route = Screen.GetStartedFirst.route) {
            GetStartedFirstScreen {
                rootNavHostController.navigate(
                    Screen.GetStartedSecond.route,
                    NavOptions.Builder().setLaunchSingleTop(true).build()
                )
            }
        }
        composable(route = Screen.GetStartedSecond.route) {
            hiltViewModel<GetStartedViewModel>().apply {
                GetStartedSecondScreen(
                    goToLoginScreen = {
                        rootNavHostController.navigate(
                            Screen.Login.route,
                            NavOptions.Builder().setLaunchSingleTop(true).build()
                        )
                    },
                    getStarted = { updateStarted(true) }
                )
            }

        }
        composable(route = Screen.Login.route) {
            hiltViewModel<LoginViewModel>().apply {
                LoginScreen(
                    goToHomeScreen = {
                        rootNavHostController.navigate(
                            Screen.Login.route,
                            NavOptions.Builder()
                                .setLaunchSingleTop(true)
                                .setPopUpTo(Screen.Splash.route, true)
                                .build()
                        )
                        rootNavHostController.navigate(
                            Screen.Home.route,
                        )
                    },
                    goToResetPassword = {
                        rootNavHostController.navigate(
                            Screen.ResetPassword.route,
                            NavOptions.Builder().setLaunchSingleTop(true).build()
                        )
                    },
                    finishActivity = {
                        finishActivity()
                    },
                    login = { email, password -> login(email, password) },
                    userEmail = appConfigPreferences.userEmail,
                    username = appConfigPreferences.userName,
                    onboarded = appConfigPreferences.onBoarded
                )
            }

        }

        composable(route = Screen.ResetPassword.route) {
            hiltViewModel<ResetPasswordViewModel>().apply {
                ResetPasswordScreen(
                    goToVerifyEmailScreen = { email ->
                        verifiedEmail = email
                        rootNavHostController.navigate(
                            Screen.VerifyEmail.route,
                            NavOptions.Builder().setLaunchSingleTop(true).build()
                        )
                    },
                    onBackArrowPressed = {
                        rootNavHostController.popBackStack()
                    },
                    resetPassword = { email ->
                        forgotPasswordVerifyEmail(email)
                    }
                )
            }

        }

        composable(route = Screen.VerifyEmail.route) {
            VerifyEmailScreen(
                goToChangePasswordScreen = {
                    rootNavHostController.navigate(
                        Screen.ChangePassword.route,
                        NavOptions.Builder().setLaunchSingleTop(true).build()
                    )
                },
                onBackArrowPressed = {
                    rootNavHostController.popBackStack()
                },
                verifiedEmail = verifiedEmail
            )
        }

        composable(route = Screen.ChangePassword.route) {
            hiltViewModel<ChangePasswordViewModel>().apply {
                ChangePasswordScreen(
                    onSuccessDialogOkayPressed = {
                        rootNavHostController.navigate(
                            Screen.Login.route,
                            NavOptions
                                .Builder()
                                .setLaunchSingleTop(true)
                                .setPopUpTo(Screen.Splash.route, true)
                                .build()
                        )
                        rootNavHostController.navigate(
                            Screen.Home.route,
                        )
                    },
                    goToLogin = {
                        rootNavHostController.navigate(
                            Screen.Login.route,
                            NavOptions
                                .Builder()
                                .setLaunchSingleTop(true)
                                .setPopUpTo(Screen.Splash.route, true)
                                .build()
                        )
                    },
                    onBackArrowPressed = {
                        rootNavHostController.popBackStack()
                    },
                    isForgotPassWord = true,
                    okayButtonText = "Go To Dashboard",
                    changePassword = { currentPassword, newPassword ->
                        changePassword(currentPassword, newPassword)
                    }
                )
            }

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
                        NavOptions
                            .Builder()
                            .setLaunchSingleTop(true)
                            .setPopUpTo(Screen.Splash.route, true)
                            .build()
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
                    Timber.d("Go to destination -> $route")
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
            hiltViewModel<AddFundsViewModel>().apply {
                AddFundsScreenModalBottomSheetLayer(
                    goBackToHomeScreen = {
                        rootNavHostController.popBackStack()
                    },
                    initiatePayStackPayment = { amount: Int ->
                        initiatePayStackPayment(amountToPay = amount)
                    },
                    paymentMethod = appConfigPreferences.paymentMethod
                )
            }
        }

    }


}

