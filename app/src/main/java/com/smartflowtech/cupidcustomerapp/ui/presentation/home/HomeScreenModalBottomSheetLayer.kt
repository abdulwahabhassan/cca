package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.model.Product
import com.smartflowtech.cupidcustomerapp.model.Status
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenModalBottomSheetLayer(
    viewModel: HomeScreenViewModel,
    bottomNavBarNavHostController: NavHostController,
    goToLogin: () -> Unit,
    isNavDestinationSelected: (String) -> Boolean,
    popBackStackOrFinishActivity: () -> Unit,
    goToDestination: (String) -> Unit,
    goToAddFundsScreen: () -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = spring()
    )
    val coroutineScope = rememberCoroutineScope()

    HomeScreenModalBottomSheet(
        modalBottomSheetState = modalBottomSheetState,
        bottomNavBarNavHostController = bottomNavBarNavHostController,
        goTo = {},
        isNavDestinationSelected = isNavDestinationSelected,
        onBackPressed = {
            if (modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                }
            } else {
                popBackStackOrFinishActivity()
            }
        },
        onBottomNavItemClicked = goToDestination,
        onFilteredClicked = {
            if (!modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded, spring())
                }
            }
        },
        onAddFundsClicked = goToAddFundsScreen,
//        userName = viewModel.appConfigPreferences.userName,
//        "John Doe",
//        walletBalanceVisibility = viewModel.appConfigPreferences.walletBalanceVisibility,
//        true,
        homeScreenUiState = viewModel.homeScreenUiState,
        appConfigPreferences = viewModel.appConfigPreferences,
//        daysFilter = viewModel.appConfigPreferences.daysFilter,
//        completedStatusFilter = viewModel.appConfigPreferences.completedStatusFilter,
//        failedStatusFilter = viewModel.appConfigPreferences.failedStatusFilter,
//        pendingStatusFilter = viewModel.appConfigPreferences.pendingStatusFilter,
//        dpkProductFilter = viewModel.appConfigPreferences.dpkProductFilter,
//        pmsProductFilter = viewModel.appConfigPreferences.pmsProductFilter,
//        agoProductFilter = viewModel.appConfigPreferences.agoProductFilter,
        onDaysFilterSelected = { date ->
            viewModel.updateDateFilter(date.toLong())
        },
        onStatusFilterSelected = { bool, type ->
            when (type) {
                Status.COMPLETED.name -> viewModel.updateCompletedStatusFilter(bool)
                Status.FAILED.name -> viewModel.updateFailedStatusFilter(bool)
                Status.PENDING.name -> viewModel.updatePendingStatusFilter(bool)
            }
        },
        onProductFilterSelected = { bool, type ->
            when (type) {
                Product.PMS.name -> viewModel.updatePmsProduct(bool)
                Product.AGO.name -> viewModel.updateAgoProduct(bool)
                Product.DPK.name -> viewModel.updateDpkProduct(bool)
            }
        },
        updateWalletVisibility = { visibility ->
            viewModel.updateWalletBalanceVisibility(visibility)
        },
        onLogoutClicked = {
            viewModel.logOut()
            goToLogin()
        },
        getTransactions = {
            viewModel.getTransactionsAndWallets()
        }
    )
}