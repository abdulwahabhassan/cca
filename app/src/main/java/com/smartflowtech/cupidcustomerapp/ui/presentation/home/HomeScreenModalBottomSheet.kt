package com.smartflowtech.cupidcustomerapp.ui.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.smartflowtech.cupidcustomerapp.R
import com.smartflowtech.cupidcustomerapp.model.Transaction
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.Success
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.CustomDateSearch
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.FilterTransactions

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenModalBottomSheet(
    modalBottomSheetState: ModalBottomSheetState,
    bottomNavBarNavHostController: NavHostController,
    goTo: () -> Unit,
    isNavDestinationSelected: (String) -> Boolean,
    onBackPressed: () -> Unit,
    onBottomNavItemClicked: (String) -> Unit,
    onFilteredClicked: () -> Unit,
    onAddFundsClicked: () -> Unit,
    userName: String,
    walletBalanceVisibility: Boolean,
    updateWalletVisibility: (Boolean) -> Unit,
    dateFilter: String,
    statusFilter: String,
    productFilter: String,
    onDateFilterSelected: (String) -> Unit,
    onStatusFilterSelected: (String) -> Unit,
    onProductFilterSelected: (String) -> Unit
) {

    var showCustomSearch: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var showSuccess: Boolean by rememberSaveable {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = modalBottomSheetState) {
        if (!modalBottomSheetState.isVisible) {
            showCustomSearch = false
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier.navigationBarsPadding(),
        sheetState = modalBottomSheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContentColor = Color.Transparent,
        sheetContent = {

            Column {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(LocalConfiguration.current.screenHeightDp.dp * 0.12f)
                )

                Column(
                    Modifier
                        .padding(top = 2.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                        )
                        .fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        IconButton(
                            onClick = { },
                            modifier = Modifier
                                .padding(end = 8.dp),
                            enabled = false
                        ) {}

                        Icon(
                            modifier = Modifier
                                .width(40.dp),
                            painter = painterResource(id = R.drawable.ic_bottom_sheet_handle_inactive),
                            contentDescription = "Bottom sheet handle",
                            tint = Color.Unspecified
                        )

                        //Close button
                        IconButton(
                            onClick = {
                                onBackPressed()
                                showCustomSearch = false
                                showSuccess = false
                            },
                            modifier = Modifier
                                .padding(end = 8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.Close,
                                contentDescription = "Close icon",
                                tint = Color.Black
                            )
                        }
                    }

                    if (showCustomSearch) {
                        CustomDateSearch(
                            onGoBackToFilterPressed = {
                                showCustomSearch = false
                            },
                            onShowSuccess = {
                                showCustomSearch = false
                                showSuccess = true
                            }
                        )
                    } else if (showSuccess) {
                        Success(
                            message = "Sent",
                            info = "We've sent the requested statements to your email",
                            onOkayPressed = {
                                onBackPressed()
                                showCustomSearch = false
                                showSuccess = false
                            }
                        )
                    } else {
                        FilterTransactions(
                            dateFilter = dateFilter,
                            statusFilter = statusFilter,
                            productFilter = productFilter,
                            onCustomSearchClicked = {
                                showCustomSearch = !showCustomSearch
                            },
                            onDateFilterSelected = onDateFilterSelected,
                            onStatusFilterSelected = onStatusFilterSelected,
                            onProductFilterSelected = onProductFilterSelected
                        )
                    }
                }
            }
        }
    ) {
        HomeScreen(
            bottomNavBarNavHostController = bottomNavBarNavHostController,
            goTo = {},
            isNavDestinationSelected = isNavDestinationSelected,
            onBackPressed = onBackPressed,
            onBottomNavItemClicked = onBottomNavItemClicked,
            onFilteredClicked = onFilteredClicked,
            onAddFundsClicked = onAddFundsClicked,
            userName = userName,
            walletBalanceVisibility = walletBalanceVisibility,
            updateWalletVisibility = updateWalletVisibility
        )
    }
}
