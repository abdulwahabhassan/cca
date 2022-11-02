package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.AddFundsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddFundsScreenModalBottomSheetLayer(
    viewModel: AddFundsViewModel,
    goBackToHomeScreen: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = spring()
    )
    val coroutineScope = rememberCoroutineScope()
    var showBanks by remember { mutableStateOf(false) }
    var selectedBank by remember { mutableStateOf("") }
    val selectedPaymentMode = remember { mutableStateOf("") }

    LaunchedEffect(key1 = modalBottomSheetState.isVisible, block = {
        if (!modalBottomSheetState.isVisible) {
            showBanks = false
        }
    })

    AddFundsScreenModalBottomSheet(
        modalBottomSheetState = modalBottomSheetState,
        onBackPressed = {
            goBackToHomeScreen()
        },
        closeModalBottomSheet = {
            if (showBanks) {
                showBanks = false
            } else {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                }
            }
        },
        onShowBanksClicked = {
            showBanks = true
        },
        showBanks = showBanks,
        selectedBank = selectedBank,
        onSelectBank = {
            selectedBank = it.name ?: ""
        },
        selectedPaymentMode = selectedPaymentMode.value,
        onSelectPaymentMode = {
            selectedPaymentMode.value = it.name
            coroutineScope.launch {
                modalBottomSheetState.show()
            }
        },
        goToHome = goBackToHomeScreen,
        paymentMethod = viewModel.appConfigPreferences.paymentMethod
    )
}

