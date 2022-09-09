package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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

    AddFundsScreenModalBottomSheet(
        modalBottomSheetState = modalBottomSheetState,
        onBackPressed = {
            goBackToHomeScreen()
        },
        onClosePressed = {
            if (modalBottomSheetState.isVisible) {
                coroutineScope.launch {
                    modalBottomSheetState.hide()
                }
            } else {
                goBackToHomeScreen()
            }
        },
        showModalBottomSheet = {
            coroutineScope.launch {
                modalBottomSheetState.show()
            }

        }
    )
}