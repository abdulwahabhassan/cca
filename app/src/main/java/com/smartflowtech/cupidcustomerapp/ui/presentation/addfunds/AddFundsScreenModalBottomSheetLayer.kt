package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import com.smartflowtech.cupidcustomerapp.model.PaymentMode
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.AddFundsScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddFundsScreenModalBottomSheetLayer(
    viewModel: AddFundsScreenViewModel,
    goBackToHomeScreen: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = spring()
    )
    val coroutineScope = rememberCoroutineScope()
    var showBanks by remember { mutableStateOf(false) }
    var selectedBank by remember { mutableStateOf("") }
    val selectedPaymentMode = remember { mutableStateOf(PaymentMode.CARD) }

    LaunchedEffect(key1 = modalBottomSheetState.isVisible, block = {
        if (!modalBottomSheetState.isVisible) {
            showBanks = false
        }
    })

    AddFundsScreenModalBottomSheet(
        modalBottomSheetState = modalBottomSheetState,
        onBackPressed = {
            if (showBanks) {
                showBanks = false
            } else {
                goBackToHomeScreen()
            }
        },
        onClosePressed = {
            if (showBanks) {
                showBanks = false
            } else {
                if (modalBottomSheetState.isVisible) {
                    coroutineScope.launch {
                        modalBottomSheetState.hide()
                    }
                } else {
                    goBackToHomeScreen()
                }
            }
        },
        onShowBanksClicked = {
            showBanks = true
        },
        showBanks = showBanks,
        selectedBank = selectedBank,
        onSelectBank = {
            selectedBank = it.name
        },
        selectedPaymentMode = selectedPaymentMode.value,
        onSelectPaymentMode = {
            selectedPaymentMode.value = it
            coroutineScope.launch {
                modalBottomSheetState.show()
            }
        }
    )
}

