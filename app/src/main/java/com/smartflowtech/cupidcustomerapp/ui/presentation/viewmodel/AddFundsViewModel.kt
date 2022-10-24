package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.PaymentRepository
import com.smartflowtech.cupidcustomerapp.model.request.VerifyPaymentRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds.VerifyPayStackPaymentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddFundsViewModel @Inject constructor(
    dataStorePrefsRepository: DataStorePrefsRepository,
    private val paymentRepository: PaymentRepository
) : BaseViewModel(dataStorePrefsRepository) {

    var verifyPayStackPaymentState by mutableStateOf(
        VerifyPayStackPaymentState(
            viewModelResult = ViewModelResult.INITIAL,
            data = null
        )
    )
        private set

    fun verifyPayStackPayment(verifyPaymentRequestBody: VerifyPaymentRequestBody) {
        viewModelScope.launch {

            verifyPayStackPaymentState =
                VerifyPayStackPaymentState(viewModelResult = ViewModelResult.LOADING)

            when (val repositoryResult = paymentRepository.verifyPayStackPayment(
                token = appConfigPreferences.token,
                verifyPaymentRequestBody = verifyPaymentRequestBody
            )) {
                is RepositoryResult.Success -> {
                    repositoryResult.data?.let { data ->
                        verifyPayStackPaymentState =
                            VerifyPayStackPaymentState(
                                viewModelResult = ViewModelResult.SUCCESS,
                                data = data
                            )
                    }
                }
                is RepositoryResult.Error -> {
                    verifyPayStackPaymentState =
                        VerifyPayStackPaymentState(
                            viewModelResult = ViewModelResult.ERROR
                        )
                }
                is RepositoryResult.Local -> {}
            }
        }
    }

}