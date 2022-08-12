package com.smartflowtech.cupidcustomerapp.ui.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun CupidCustomerAppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(goToHomeScreen = {
                navController.navigate(
                    Screen.Home.route,
                    NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                )
            }, goToGetStartedScreen = {
                navController.navigate(
                    Screen.GetStartedFirst.route,
                    NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                )
            }, goToLoginScreen = {
                navController.navigate(
                    Screen.Login.route,
                    NavOptions.Builder().setPopUpTo(Screen.Splash.route, true).build()
                )
            })
        }
        composable(route = Screen.GetStartedFirst.route) {
            GetStartedFirstScreen {
                navController.navigate(
                    Screen.GetStartedSecond.route
                )
            }
        }
        composable(route = Screen.GetStartedSecond.route) {
            GetStartedSecondScreen {
                navController.navigate(
                    Screen.Login.route
                )
            }
        }
        composable(route = Screen.Login.route) {
            LoginScreen(goToHomeScreen = {
                navController.navigate(
                    Screen.Home.route
                )
            }, {})
        }
        composable(route = Screen.Home.route) {
            HomeScreen({})
        }
    }

}