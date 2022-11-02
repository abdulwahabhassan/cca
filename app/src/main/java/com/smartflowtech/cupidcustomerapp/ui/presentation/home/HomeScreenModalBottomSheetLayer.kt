package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.ModalBottomSheetContent
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenModalBottomSheetLayer(
    viewModel: HomeViewModel,
    bottomNavBarNavHostController: NavHostController,
    goToLogin: () -> Unit,
    isNavDestinationSelected: (String) -> Boolean,
    popBackStackOrFinishActivity: () -> Unit,
    goToDestination: (String) -> Unit,
    goToAddFundsScreen: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = spring()
    )
    val coroutineScope = rememberCoroutineScope()
    var modalBottomSheetContent: String by rememberSaveable {
        mutableStateOf(ModalBottomSheetContent.FilterTransactions.contentKey)
    }

    LaunchedEffect(key1 = modalBottomSheetState.isVisible, block = {
        if (!modalBottomSheetState.isVisible) {
            modalBottomSheetContent = ModalBottomSheetContent.FilterTransactions.contentKey
        }
    })

    HomeScreenModalBottomSheet(
        modalBottomSheetState = modalBottomSheetState,
        bottomNavBarNavHostController = bottomNavBarNavHostController,
        isNavDestinationSelected = isNavDestinationSelected,
        goBack = {
            if (modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                    modalBottomSheetContent = ModalBottomSheetContent.FilterTransactions.contentKey
                }
            } else {
                popBackStackOrFinishActivity()
            }
        },
        onBottomNavItemClicked = goToDestination,
        onFilteredClicked = {
            if (!modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.animateTo(
                        ModalBottomSheetValue.HalfExpanded, spring()
                    )
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
            goToLogin()
        },
        getTransactions = {
            viewModel.getTransactionsAndWallets()
        },
        persistProfilePicture = { uri ->
            viewModel.persistProfilePicture(uri)
        },
        profilePicture = viewModel.appConfigPreferences.profilePictureUri,
        onStationFilterSelected = { filter ->
            viewModel.updateStationFilter(filter)
        },
        setModalBottomSheetContent = { contentKey ->
            modalBottomSheetContent = contentKey
            if (!modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.animateTo(
                        ModalBottomSheetValue.HalfExpanded, spring()
                    )
                }
            }
        },
        modalBottomSheetContentKey = modalBottomSheetContent
    )
}