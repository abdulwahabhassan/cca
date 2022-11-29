package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.model.domain.Period
import com.smartflowtech.cupidcustomerapp.model.domain.CardHistoryPeriodFilterContext
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreen
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.HomeScreenModalBottomSheetContent
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenModalBottomSheetLayer(
    viewModel: HomeViewModel,
    bottomNavBarNavHostController: NavHostController,
    goToLogin: () -> Unit,
    isNavDestinationSelected: (String) -> Boolean,
    popBackStackOrFinishActivity: () -> Unit,
    goToDestination: (String) -> Unit,
    goToAddFundsScreen: () -> Unit,
    onCompleteOnBoarding: () -> Unit,
    isOnBoarded: Boolean
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = spring()
    )
    val coroutineScope = rememberCoroutineScope()
    var homeScreenModalBottomSheetContent: String by rememberSaveable {
        mutableStateOf(HomeScreenModalBottomSheetContent.TransactionsFilter.contentKey)
    }
    var selectedMonthYearPeriod by remember {
        mutableStateOf(LocalDate.now().format(DateTimeFormatter.ofPattern("MMM yyyy")))
    }

    LaunchedEffect(key1 = modalBottomSheetState.isVisible, block = {
        if (!modalBottomSheetState.isVisible) {
            homeScreenModalBottomSheetContent =
                HomeScreenModalBottomSheetContent.TransactionsFilter.contentKey
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
                    homeScreenModalBottomSheetContent =
                        HomeScreenModalBottomSheetContent.TransactionsFilter.contentKey
                }
            } else {
                popBackStackOrFinishActivity()
            }
        },
        onBottomNavItemClicked = goToDestination,
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
            homeScreenModalBottomSheetContent = contentKey
            if (!modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.animateTo(
                        ModalBottomSheetValue.Expanded, spring()
                    )
                }
            }
        },
        modalBottomSheetContentKey = homeScreenModalBottomSheetContent,
        onDaysPeriodFilterSelected = { filter, context ->
            when (bottomNavBarNavHostController.currentDestination?.route) {
                HomeScreen.Notifications.route -> viewModel.updateNotificationPeriodFilter(filter)
                HomeScreen.Home.route, HomeScreen.Transactions.route -> {
                    when (context) {
                        CardHistoryPeriodFilterContext.DEFAULT -> {
                            viewModel.updateTransactionPeriodFilter(filter)
                        }
                        CardHistoryPeriodFilterContext.MONTH_YEAR -> {
                            //to be changed to one year
                            viewModel.updateTransactionPeriodFilter(Period.TWO_YEARS.name)
                            selectedMonthYearPeriod = filter
                        }
                    }
                }
            }
        },
        printTransactionReport = { from, to ->
            viewModel.getTransactionReport(dateFrom = from, dateTo = to)
        },
        selectedMonthYearPeriod = selectedMonthYearPeriod,
        onCompleteOnBoarding = onCompleteOnBoarding,
        isOnBoarded = isOnBoarded
    )
}