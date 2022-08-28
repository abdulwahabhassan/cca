package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenModalBottomSheetLayer(
    viewModel: HomeScreenViewModel,
    bottomNavBarNavHostController: NavHostController,
    goTo: () -> Unit,
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
        onDownloadTransactionPressed = {

        },
        onAddFundsClicked = goToAddFundsScreen
    )
}