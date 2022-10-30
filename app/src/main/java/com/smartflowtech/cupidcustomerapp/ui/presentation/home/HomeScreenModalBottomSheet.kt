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
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.ui.presentation.common.Success
import com.smartflowtech.cupidcustomerapp.ui.presentation.profile.UploadImage
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.DownloadTransactions
import com.smartflowtech.cupidcustomerapp.ui.presentation.transactions.FilterTransactions

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreenModalBottomSheet(
    modalBottomSheetState: ModalBottomSheetState,
    bottomNavBarNavHostController: NavHostController,
    isNavDestinationSelected: (String) -> Boolean,
    goBack: () -> Unit,
    onBottomNavItemClicked: (String) -> Unit,
    onFilteredClicked: () -> Unit,
    onAddFundsClicked: () -> Unit,
    updateWalletVisibility: (Boolean) -> Unit,
    appConfigPreferences: DataStorePrefsRepository.AppConfigPreferences,
    onSaveFilterClicked: (String, Map<String, Boolean>) -> Unit,
    homeScreenUiState: HomeScreenUiState,
    onLogoutClicked: () -> Unit,
    getTransactions: () -> Unit,
    shouldShowDownloadTransactions: Boolean,
    shouldShowSuccess: Boolean,
    showSuccess: (Boolean) -> Unit,
    showDownloadTransactions: (Boolean) -> Unit,
    shouldShowUploadImage: Boolean,
    showUploadImage: (Boolean) -> Unit,
    persistProfilePicture: (String) -> Unit,
    profilePicture: String,
) {

    var successTitle: String by rememberSaveable { mutableStateOf("Success") }
    var successMessage: String by rememberSaveable { mutableStateOf("") }

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
                            onClick = goBack,
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

                    if (shouldShowUploadImage) {
                        UploadImage(
                            onImageSelected = { uri ->
                                persistProfilePicture(uri)
                                goBack()
                            }
                        )
                    } else if (shouldShowDownloadTransactions) {
                        DownloadTransactions(
                            showSuccess = {
                                successTitle = "Sent"
                                successMessage = "We've sent the requested statements to your email"
                                showSuccess(true)
                            }
                        )
                    } else if (shouldShowSuccess) {
                        Success(
                            title = successTitle,
                            message = successMessage,
                            onOkayPressed = goBack
                        )
                    } else {
                        FilterTransactions(
                            appConfigPreferences = appConfigPreferences,
                            onFilterSaveClicked = onSaveFilterClicked,
                            onBackPressed = goBack
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
            onBackPressed = goBack,
            onBottomNavItemClicked = onBottomNavItemClicked,
            onFilteredClicked = onFilteredClicked,
            onAddFundsClicked = onAddFundsClicked,
            userFullName = appConfigPreferences.fullName,
            userName = appConfigPreferences.userName,
            walletBalanceVisibility = appConfigPreferences.walletBalanceVisibility,
            updateWalletVisibility = updateWalletVisibility,
            homeScreenUiState = homeScreenUiState,
            onLogOutClicked = onLogoutClicked,
            getTransactions = getTransactions,
            onDownloadTransactionsClicked = {
                showDownloadTransactions(true)
            },
            onUploadImageClicked = {
                showUploadImage(true)
            },
            onProfileUpdateSuccess = {
                successTitle = "Successful"
                successMessage = "Profile updated"
                showSuccess(true)
                goBack()

            },
            profilePicture = profilePicture
        )
    }
}
