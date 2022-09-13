package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
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

    var shouldShowDownloadTransactions: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var shouldShowSuccess: Boolean by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = modalBottomSheetState.isVisible, block = {
        if (!modalBottomSheetState.isVisible) {
            shouldShowSuccess = false
            shouldShowDownloadTransactions = false
        }
    })

    HomeScreenModalBottomSheet(
        modalBottomSheetState = modalBottomSheetState,
        bottomNavBarNavHostController = bottomNavBarNavHostController,
        goTo = {},
        isNavDestinationSelected = isNavDestinationSelected,
        onBackPressed = {
            if (modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                    shouldShowSuccess = false
                    shouldShowDownloadTransactions = false
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
        homeScreenUiState = viewModel.homeScreenUiState,
        appConfigPreferences = viewModel.appConfigPreferences,
        onSaveFilterClicked = { daysFilter, mapOfFilters ->

            viewModel.updateTransactionFilters(daysFilter, mapOfFilters)

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
        },
        shouldShowDownloadTransactions = shouldShowDownloadTransactions,
        showDownloadTransactions = { bool ->
            shouldShowDownloadTransactions = bool
            shouldShowSuccess = !bool
            if (!modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded, spring())
                }
            }
        },
        shouldShowSuccess = shouldShowSuccess,
        showSuccess = { bool ->
            shouldShowSuccess = bool
            shouldShowDownloadTransactions = !bool
            if (!modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded, spring())
                }
            }
        }
    )
}