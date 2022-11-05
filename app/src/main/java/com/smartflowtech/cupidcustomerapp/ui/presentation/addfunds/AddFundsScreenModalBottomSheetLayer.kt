package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import co.paystack.android.model.Card
import com.smartflowtech.cupidcustomerapp.model.request.PayStackPayment
import com.smartflowtech.cupidcustomerapp.model.request.PayStackPaymentRequestBody
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.AddFundsModalBottomSheetContent
import com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel.AddFundsViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddFundsScreenModalBottomSheetLayer(
    viewModel: AddFundsViewModel,
    goBackToHomeScreen: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = spring()
    )
    var ussdScreenContent by remember {
        mutableStateOf(AddFundsModalBottomSheetContent.Banks.contentKey)
    }
    val selectedPaymentMode = remember { mutableStateOf("") }

    LaunchedEffect(key1 = modalBottomSheetState.isVisible, block = {
        if (!modalBottomSheetState.isVisible) {
            ussdScreenContent = AddFundsModalBottomSheetContent.Banks.contentKey
        }
    })

    AddFundsScreenModalBottomSheet(
        modalBottomSheetState = modalBottomSheetState,
        onBackPressed = {
            goBackToHomeScreen()
        },
        closeModalBottomSheet = {
            coroutineScope.launch {
                modalBottomSheetState.hide()
            }
        },
        selectedPaymentMode = selectedPaymentMode.value,
        onSelectPaymentMode = {
            selectedPaymentMode.value = it.name
            coroutineScope.launch {
                modalBottomSheetState.show()
            }
        },
        goToHome = goBackToHomeScreen,
        paymentMethod = viewModel.appConfigPreferences.paymentMethod,
        setUssdScreenContent = {
            ussdScreenContent = it
        },
        ussdScreenContent = ussdScreenContent,
        initiatePayStackPayment = { amount: Int ->
            viewModel.initiatePayStackPayment(amountToPay = amount)
        }
    )
}

