package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.smartflowtech.cupidcustomerapp.model.PaymentMode

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddFundsScreenModalBottomSheet(
    modalBottomSheetState: ModalBottomSheetState,
    onBackPressed: () -> Unit,
    onClosePressed: () -> Unit,
    showModalBottomSheet: () -> Unit
) {

    val selectedPaymentMode = remember { mutableStateOf(PaymentMode.CARD) }

    BackHandler {
        onClosePressed()
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
                        horizontalArrangement = Arrangement.End
                    ) {
                        //Close button
                        IconButton(
                            onClick = {
                                onClosePressed()
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
                    when (selectedPaymentMode.value) {
                        PaymentMode.BANK_TRANSFER -> AddFundsBankPaymentMode(
                            "35647729920",
                            "Wema Bank"
                        )
                        PaymentMode.USSD -> AddFundsUssdPaymentMode(code = "*243*904*09382")
                        PaymentMode.CARD -> { }
                    }


                }
            }
        }
    ) {
        AddFundsScreen(onBackPressed = onBackPressed, onPaymentModeSelected = { mode ->
            selectedPaymentMode.value = mode
            showModalBottomSheet()
        })

    }
}