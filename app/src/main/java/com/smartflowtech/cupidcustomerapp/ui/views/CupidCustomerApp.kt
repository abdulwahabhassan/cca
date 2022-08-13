package com.smartflowtech.cupidcustomerapp.ui.views

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CupidCustomerApp() {
    CupidCustomerAppNavigation(navHostController = rememberNavController())
}