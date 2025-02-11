package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.animation.core.spring
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.*
import com.smartflowtech.cupidcustomerapp.model.domain.PaymentMode
import com.smartflowtech.cupidcustomerapp.model.request.FundWallet
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.AddFundsModalBottomSheetContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddFundsScreenModalBottomSheetLayer(
    goBackToHomeScreen: (refresh: Boolean) -> Unit,
    initiatePayStackPayment: suspend (amount: Int) -> PayStackPaymentState,
    paymentMethod: String,
    vendorBankAccountNumber: String,
    vendorBankName: String,
    fundWalletAfterPayStackPayment: suspend (amount: Int, reference: String) -> FundWalletState,
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
            goBackToHomeScreen(false)
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
                if (it == PaymentMode.USSD) {
                    modalBottomSheetState.animateTo(ModalBottomSheetValue.Expanded)
                } else {
                    modalBottomSheetState.show()
                }
            }
        },
        goToHome = { goBackToHomeScreen(true) },
        paymentMethod = paymentMethod,
        setUssdScreenContent = {
            ussdScreenContent = it
        },
        ussdScreenContent = ussdScreenContent,
        initiatePayStackPayment = initiatePayStackPayment,
        vendorBankAccountNumber = vendorBankAccountNumber,
        vendorBankName = vendorBankName,
        fundWalletAfterPayStackPayment = fundWalletAfterPayStackPayment
    )
}

