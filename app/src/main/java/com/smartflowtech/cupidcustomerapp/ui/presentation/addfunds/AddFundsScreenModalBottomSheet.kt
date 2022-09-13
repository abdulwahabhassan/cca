package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.model.Bank
import com.smartflowtech.cupidcustomerapp.model.PaymentMode

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddFundsScreenModalBottomSheet(
    modalBottomSheetState: ModalBottomSheetState,
    onBackPressed: () -> Unit,
    closeModalBottomSheet: () -> Unit,
    showBanks: Boolean,
    onShowBanksClicked: () -> Unit,
    selectedBank: String,
    selectedPaymentMode: String,
    onSelectBank: (Bank) -> Unit,
    onSelectPaymentMode: (PaymentMode) -> Unit,
    goToHome: () -> Unit
) {

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
                        horizontalArrangement = Arrangement.End
                    ) {
                        //Close button
                        IconButton(
                            onClick = {
                                closeModalBottomSheet()
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
                    when (selectedPaymentMode) {
                        PaymentMode.BANK_TRANSFER.name -> AddFundsBankPaymentMode(
                            modalBottomSheetState = modalBottomSheetState,
                            "35647729920",
                            "Wema Bank",
                            onBackPressed = closeModalBottomSheet
                        )
                        PaymentMode.USSD.name -> AddFundsUssdPaymentMode(
                            modalBottomSheetState = modalBottomSheetState,
                            code = "*243*904*09382",
                            onShowBanksClicked = onShowBanksClicked,
                            showBanks = showBanks,
                            selectedBank = selectedBank,
                            onSelectBank = onSelectBank,
                            onBackPressed = closeModalBottomSheet
                        )
                        PaymentMode.CARD.name -> AddFundsSelectCardPaymentProcessor(
                            modalBottomSheetState = modalBottomSheetState,
                            onBackPressed = closeModalBottomSheet,
                            onDismissErrorDialog = closeModalBottomSheet,
                            onDismissSuccessDialog = goToHome
                        )
                    }
                }
            }
        }
    ) {
        AddFundsScreen(
            onBackPressed = onBackPressed,
            onPaymentModeSelected = { mode ->
                onSelectPaymentMode(mode)
            }
        )

    }
}