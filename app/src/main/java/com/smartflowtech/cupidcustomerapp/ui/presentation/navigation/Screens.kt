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
    object SecuritySettings : HomeScreen(
        "home_screen/security_settings",
        "Change Password",
        R.drawable.ic_security
    )
    object NotificationSettings : HomeScreen(
        "home_screen/notification_settings",
        "Notifications Settings",
        R.drawable.ic_notification
    )
    object PaymentSettings : HomeScreen(
        "home_screen/payment_settings",
        "PaymentSettings Methods",
        R.drawable.ic_payment
    )
    object Notifications : HomeScreen(
        "home_screen/notifications",
        "Notifications",
        R.drawable.ic_payment
    )
}

sealed class HomeScreenModalBottomSheetContent(val contentKey: String) {


    object DownloadTransactions : HomeScreenModalBottomSheetContent("download_transactions")
    object Success : HomeScreenModalBottomSheetContent("success")
    object UploadImage : HomeScreenModalBottomSheetContent("upload_image")
    object StationsFilter : HomeScreenModalBottomSheetContent("station_filter")
    object StationDetails : HomeScreenModalBottomSheetContent("station_details")
    object TransactionsFilter : HomeScreenModalBottomSheetContent("transactions_filter")
    object DaysFilter : HomeScreenModalBottomSheetContent("days_filter")

}

sealed class AddFundsModalBottomSheetContent(val contentKey: String) {
    object Banks : HomeScreenModalBottomSheetContent("banks")
    object UssdCode : HomeScreenModalBottomSheetContent("ussd_code")
}