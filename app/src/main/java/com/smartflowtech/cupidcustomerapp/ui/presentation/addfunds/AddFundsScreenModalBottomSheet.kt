package com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds

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
import androidx.compose.ui.unit.dp
import co.paystack.android.model.Card
import com.smartflowtech.cupidcustomerapp.model.domain.Bank
import com.smartflowtech.cupidcustomerapp.model.domain.PaymentMode
import com.smartflowtech.cupidcustomerapp.ui.presentation.navigation.AddFundsModalBottomSheetContent
import com.smartflowtech.cupidcustomerapp.ui.theme.darkBlue
import com.smartflowtech.cupidcustomerapp.ui.theme.red
import com.smartflowtech.cupidcustomerapp.ui.theme.transparentPink
import com.smartflowtech.cupidcustomerapp.ui.theme.transparentPurple
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddFundsScreenModalBottomSheet(
    modalBottomSheetState: ModalBottomSheetState,
    onBackPressed: () -> Unit,
    closeModalBottomSheet: () -> Unit,
    selectedPaymentMode: String,
    onSelectPaymentMode: (PaymentMode) -> Unit,
    goToHome: () -> Unit,
    paymentMethod: String,
    setUssdScreenContent: (String) -> Unit,
    ussdScreenContent: String,
    initiatePayStackPayment: suspend (amount: Int) -> PayStackPaymentState,
    vendorBankAccountNumber: String,
    vendorBankName: String,
) {
    var amount: Int by rememberSaveable { mutableStateOf(0) }
    var selectedUssdBank: Bank? by remember { mutableStateOf(null) }

    ModalBottomSheetLayout(
        modifier = Modifier.navigationBarsPadding(),
        sheetState = modalBottomSheetState,
        sheetBackgroundColor = Color.Transparent,
        sheetElevation = 0.dp,
        sheetContent = {

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenHeightDp.dp * 0.12f)
            )

            Column(
                Modifier
                    .padding(top = 16.dp)
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
                        accountNumber = vendorBankAccountNumber,
                        bankName = vendorBankName,
                        onBackPressed = closeModalBottomSheet
                    )
                    PaymentMode.USSD.name -> {
                        AddFundsUssdPaymentMode(
                            modalBottomSheetState = modalBottomSheetState,
                            selectedBank = selectedUssdBank,
                            onSelectAnotherBankClicked = {
                                setUssdScreenContent(
                                    AddFundsModalBottomSheetContent.Banks.contentKey
                                )
                            },
                            onSelectUssdBank = { bank ->
                                selectedUssdBank = bank
                                setUssdScreenContent(
                                    AddFundsModalBottomSheetContent.UssdCode.contentKey
                                )
                            },
                            onBackPressed = closeModalBottomSheet,
                            ussdScreenContent = ussdScreenContent
                        )
                    }
                    PaymentMode.CARD.name -> AddFundsSelectCardPaymentProcessor(
                        modalBottomSheetState = modalBottomSheetState,
                        onBackPressed = closeModalBottomSheet,
                        onDismissErrorDialog = closeModalBottomSheet,
                        onDismissSuccessDialog = goToHome,
                        paymentMethod = paymentMethod,
                        amount = amount,
                        initiatePayStackPaymentState = initiatePayStackPayment
                    )
                }
            }
        }
    ) {
        AddFundsScreen(
            onBackPressed = onBackPressed,
            onPaymentModeSelected = { mode ->
                onSelectPaymentMode(mode)
            },
            onProceedClicked = {
                amount = it
            }
        )

    }
}