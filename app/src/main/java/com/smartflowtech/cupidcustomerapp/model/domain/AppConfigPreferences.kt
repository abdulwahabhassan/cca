package com.smartflowtech.cupidcustomerapp.model.domain

data class AppConfigPreferences(
    val onBoarded: Boolean = false,
    val loggedIn: Boolean = false,
    val userName: String = "",
    val userEmail: String = "",
    val token: String = "",
    val phoneNumber: String = "",
    val walletBalanceVisibility: Boolean = true,
    val transactionPeriodFilter: String = Period.TWO_YEARS.name, //to be changed to one year
    val completedStatusFilter: Boolean = true,
    val failedStatusFilter: Boolean = true,
    val pendingStatusFilter: Boolean = true,
    val dpkProductFilter: Boolean = true,
    val agoProductFilter: Boolean = true,
    val pmsProductFilter: Boolean = true,
    val companyId: String = "",
    val userId: String = "",
    val fullName: String = "",
    val profilePictureUri: String = "",
    val emailNotifications: Boolean = false,
    val pushNotifications: Boolean = false,
    val paymentMethod: String = PaymentMethodPreference.ASK_ALWAYS.name,
    val stationFilter: String = StationFilter.STATE.name,
    val notificationPeriodFilter: String = Period.ONE_MONTH.name,
    val vendorId: Long = -1,
    val vendorAccountNumber: String = "",
    val vendorAccountName: String = "",
    val vendorBankName: String = "",
    val paymentModeId: String = ""
) {

}
