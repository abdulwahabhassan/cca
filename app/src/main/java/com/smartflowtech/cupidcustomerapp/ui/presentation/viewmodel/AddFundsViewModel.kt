package com.smartflowtech.cupidcustomerapp.ui.presentation.viewmodel

import com.smartflowtech.cupidcustomerapp.data.repo.DataStorePrefsRepository
import com.smartflowtech.cupidcustomerapp.data.repo.PaymentRepository
import com.smartflowtech.cupidcustomerapp.model.request.FundWallet
import com.smartflowtech.cupidcustomerapp.model.request.FundWalletRequestBody
import com.smartflowtech.cupidcustomerapp.model.request.PayStackPayment
import com.smartflowtech.cupidcustomerapp.model.request.PayStackPaymentRequestBody
import com.smartflowtech.cupidcustomerapp.model.result.RepositoryResult
import com.smartflowtech.cupidcustomerapp.model.result.ViewModelResult
import com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds.FundWalletState
import com.smartflowtech.cupidcustomerapp.ui.presentation.addfunds.PayStackPaymentState
import dagger.hilt.android.lifecycle.HiltViewModel
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
                        data = data,
                        userEmail = appConfigPreferences.userEmail
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

    suspend fun fundWalletAfterPayStackPayment(amountPaid: Int, reference: String): FundWalletState {
        return when (val repositoryResult = paymentRepository.funWalletAfterPayStackPayment(
            token = appConfigPreferences.token,
            fundWalletRequestBody = FundWalletRequestBody(
                payStackPayment = FundWallet(
                    paymentModeID = 1,
                    companyID = appConfigPreferences.companyId.toLong(),
                    paymentUploadedBy = appConfigPreferences.fullName,
                    amountPaid = amountPaid,
                    reference = reference,
                    channel = "PAYSTACK ONLINE"
                )
            )
        )) {
            is RepositoryResult.Success -> {
                repositoryResult.data?.let { data ->
                    FundWalletState(
                        viewModelResult = ViewModelResult.SUCCESS,
                        data = data
                    )

                } ?: FundWalletState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = "Response data not found!"
                )
            }
            is RepositoryResult.Error -> {
                FundWalletState(
                    viewModelResult = ViewModelResult.ERROR,
                    message = repositoryResult.message
                )

            }

        }
    }
}