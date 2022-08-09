package com.smartflowtech.cupidcustomerapp.ui.views


sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    object GetStartedFirst : Screen("get_started_first_screen")
    object GetStartedSecond : Screen("get_started_second_screen")
    object Login : Screen("login_screen")
}