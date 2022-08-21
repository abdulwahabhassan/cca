package com.smartflowtech.cupidcustomerapp.ui.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.CupidCustomerAppNavigation

@Composable
fun CupidCustomerApp() {
    CupidCustomerAppNavigation(navHostController = rememberNavController())
}