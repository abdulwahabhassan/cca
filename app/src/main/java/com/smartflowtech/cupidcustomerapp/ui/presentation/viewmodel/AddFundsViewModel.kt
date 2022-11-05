package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import co.paystack.android.model.Card
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.PaymentRepository
import com.smartflowtech.cupidcustomerapp.model.request.PayStackPayment
import com.smartflowtech.cupidcustomerapp.model.request.PayStackPaymentRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds.PayStackPaymentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFundsViewModel @Inject constructor(
    dataStorePrefsRepository: DataStorePrefsRepository,
    private val paymentRepository: PaymentRepository
) : BaseViewModel(dataStorePrefsRepository) {

    suspend fun initiatePayStackPayment(amountToPay: Int): PayStackPaymentState {

        return when (val repositoryResult = paymentRepository.initiatePayStackPayment(
            token = appConfigPreferences.token,
            payStackPaymentRequestBody = PayStackPaymentRequestBody(
                payStackPayment = PayStackPayment(
                    paymentModeID = 1,
                    companyID = appConfigPreferences.companyId.toLong(),
                    paymentInitiatedBy = appConfigPreferences.fullName,
                    amountToPay = amountToPay
                )
            )
        )) {
            is RepositoryResult.Success -> {
                repositoryResult.data?.let { data ->
                    PayStackPaymentState(
                        viewModelResult = ViewModelResult.SUCCESS,
                        data = data
                    )

                } ?: PayStackPaymentState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = "Response data not found!"
                )
            }
            is RepositoryResult.Error -> {
                PayStackPaymentState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = repositoryResult.message
                )

            }

        }
    }

}