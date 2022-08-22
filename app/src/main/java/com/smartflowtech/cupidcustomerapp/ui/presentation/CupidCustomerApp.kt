package com.smartflowtech.cupidcustomerapp.ui.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.CupidCustomerAppNavigation

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CupidCustomerApp() {
    CupidCustomerAppNavigation(navHostController = rememberAnimatedNavController())
}