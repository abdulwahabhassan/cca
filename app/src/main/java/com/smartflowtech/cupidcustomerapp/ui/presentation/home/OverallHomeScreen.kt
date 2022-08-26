package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.FilterTransactionsModalBottomSheet
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.HomeScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OverallHomeScreen(
    viewModel: HomeScreenViewModel,
    bottomNavBarNavHostController: NavHostController,
    goTo: () -> Unit,
    isNavDestinationSelected: (String) -> Boolean,
    onBackPressed: () -> Unit,
    onBottomNavItemClicked: (String) -> Unit,
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Expanded,
        animationSpec = spring()
    )
    val coroutineScope = rememberCoroutineScope()

    FilterTransactionsModalBottomSheet(
        modalBottomSheetState = modalBottomSheetState,
        onBackPressedFromFilterScreen = {
            if (modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                }
            }
        },
        bottomNavBarNavHostController = bottomNavBarNavHostController,
        goTo = {},
        isNavDestinationSelected = isNavDestinationSelected,
        onBackPressed = onBackPressed,
        onBottomNavItemClicked = onBottomNavItemClicked,
        onFilteredClicked = {
            if (!modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.show()
                }
            }
        }
    )

//    HomeScreen(
//        bottomNavBarNavHostController = bottomNavBarNavHostController,
//        goTo = {},
//        isNavDestinationSelected = isNavDestinationSelected,
//        onBackPressed = onBackPressed,
//        onBottomNavItemClicked = onBottomNavItemClicked,
//        onFilteredClicked = {
//            if (!modalBottomSheetState.isVisible) {
//                coroutineScope.launch {
//                    modalBottomSheetState.show()
//                }
//            }
//        }
//    )
}