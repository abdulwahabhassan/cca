package com.smartflowtech.cupidcustomerapp.ui.presentation.navigation

import com.smartflowtech.cupidcustomerapp.R


sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    object GetStartedFirst : Screen("get_started_first_screen")
    object GetStartedSecond : Screen("get_started_second_screen")
    object Login : Screen("login_screen")
    object AddFunds : Screen("add_funds_screen")
    object ResetPassword : Screen("reset_password_screen")
    object VerifyEmail : Screen("verify_email_screen")
    object ChangePassword : Screen("change_password_screen")

}

sealed class HomeScreen(val route: String, var title: String, var icon: Int) {
    object Home : HomeScreen(
        "home_screen/home",
        "Home",
        R.drawable.ic_home
    )

    object Transactions : HomeScreen(
        "home_screen/transactions",
        "Transactions",
        R.drawable.ic_receipt
    )

    object Location : HomeScreen(
        "home_screen/location",
        "Location",
        R.drawable.ic_location
    )

    object Settings : HomeScreen(
        "home_screen/settings",
        "Settings",
        R.drawable.ic_settings
    )

    object Profile : HomeScreen(
        "home_screen/profile",
        "My Profile",
        R.drawable.ic_profile
    )
    object Security : HomeScreen(
        "home_screen/security",
        "Change Password",
        R.drawable.ic_security
    )
    object Notification : HomeScreen(
        "home_screen/notification",
        "Notifications Settings",
        R.drawable.ic_notification
    )
    object Payment : HomeScreen(
        "home_screen/payment",
        "Payment Methods",
        R.drawable.ic_payment
    )
}