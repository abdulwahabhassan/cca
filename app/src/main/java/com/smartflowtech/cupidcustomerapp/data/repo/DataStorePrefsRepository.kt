package com.smartflowtech.cupidcustomerapp.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.smartflowtech.cupidcustomerapp.model.domain.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class DataStorePrefsRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val ONBOARDED = booleanPreferencesKey("onboarded")
        val LOGGED_IN = booleanPreferencesKey("loggedIn")
        val USERNAME = stringPreferencesKey("userName")
        val USER_EMAIL = stringPreferencesKey("userEmail")
        val TOKEN = stringPreferencesKey("token")
        val PHONE_NUMBER = stringPreferencesKey("phoneNumber")
        val WALLET_BALANCE_VISIBILITY = booleanPreferencesKey("walletVisibility")
        val TRANSACTION_PERIOD_FILTER = stringPreferencesKey("periodFilter")
        val COMPLETED_STATUS_FILTER = booleanPreferencesKey("completedStatusFilter")
        val FAILED_STATUS_FILTER = booleanPreferencesKey("failedStatusFilter")
        val PENDING_STATUS_FILTER = booleanPreferencesKey("pendingStatusFilter")
        val DPK_PRODUCT_FILTER = booleanPreferencesKey("dpkProductFilter")
        val PMS_PRODUCT_FILTER = booleanPreferencesKey("pmsProductFilter")
        val AGO_PRODUCT_FILTER = booleanPreferencesKey("agoProductFilter")
        val COMPANY_ID = stringPreferencesKey("companyId")
        val USER_ID = stringPreferencesKey("userId")
        val FULL_NAME = stringPreferencesKey("fullName")
        val PROFILE_PICTURE_URI = stringPreferencesKey("profilePicture")
        val EMAIL_NOTIFICATIONS = booleanPreferencesKey("emailNotifications")
        val PUSH_NOTIFICATIONS = booleanPreferencesKey("pushNotifications")
        val PAYMENT_METHOD = stringPreferencesKey("paymentMethod")
        val STATION_FILTER = stringPreferencesKey("stationFilter")
        val NOTIFICATION_PERIOD_FILTER = stringPreferencesKey("notificationsFilter")
        val VENDOR_ID = longPreferencesKey("vendorId")
        val VENDOR_BANK_ACCOUNT_NUMBER = stringPreferencesKey("vendorAccountNumber")
        val VENDOR_BANK_ACCOUNT_NAME = stringPreferencesKey("vendorAccountName")
        val VENDOR_BANK_NAME = stringPreferencesKey("vendorBanKName")
    }

    val appConfigPreferencesAsFlow: Flow<AppConfigPreferences> = dataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences -> mapUserPreferences(preferences) }

    suspend fun getAppConfigPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())

    private fun mapUserPreferences(preferences: Preferences): AppConfigPreferences {
        return AppConfigPreferences(
            onBoarded = preferences[PreferencesKeys.ONBOARDED] ?: false,
            loggedIn = preferences[PreferencesKeys.LOGGED_IN] ?: false,
            userName = preferences[PreferencesKeys.USERNAME] ?: "",
            userEmail = preferences[PreferencesKeys.USER_EMAIL] ?: "",
            token = preferences[PreferencesKeys.TOKEN] ?: "",
            phoneNumber = preferences[PreferencesKeys.PHONE_NUMBER] ?: "",
            walletBalanceVisibility = preferences[PreferencesKeys.WALLET_BALANCE_VISIBILITY]
                ?: true,
            transactionPeriodFilter = preferences[PreferencesKeys.TRANSACTION_PERIOD_FILTER] ?: Period.TWO_YEARS.name,
            completedStatusFilter = preferences[PreferencesKeys.COMPLETED_STATUS_FILTER] ?: true,
            failedStatusFilter = preferences[PreferencesKeys.FAILED_STATUS_FILTER] ?: true,
            pendingStatusFilter = preferences[PreferencesKeys.PENDING_STATUS_FILTER] ?: true,
            dpkProductFilter = preferences[PreferencesKeys.DPK_PRODUCT_FILTER] ?: true,
            pmsProductFilter = preferences[PreferencesKeys.PMS_PRODUCT_FILTER] ?: true,
            agoProductFilter = preferences[PreferencesKeys.AGO_PRODUCT_FILTER] ?: true,
            companyId = preferences[PreferencesKeys.COMPANY_ID] ?: "",
            userId = preferences[PreferencesKeys.USER_ID] ?: "",
            fullName = preferences[PreferencesKeys.FULL_NAME] ?: "",
            profilePictureUri = preferences[PreferencesKeys.PROFILE_PICTURE_URI] ?: "",
            emailNotifications = preferences[PreferencesKeys.EMAIL_NOTIFICATIONS] ?: false,
            pushNotifications = preferences[PreferencesKeys.PUSH_NOTIFICATIONS] ?: false,
            paymentMethod = preferences[PreferencesKeys.PAYMENT_METHOD] ?: PaymentMethodPreference.ASK_ALWAYS.name,
            stationFilter = preferences[PreferencesKeys.STATION_FILTER] ?: StationFilter.STATE.name,
            notificationPeriodFilter = preferences[PreferencesKeys.NOTIFICATION_PERIOD_FILTER] ?: Period.ONE_MONTH.name,
            vendorId = preferences[PreferencesKeys.VENDOR_ID] ?: -1,
            vendorAccountName = preferences[PreferencesKeys.VENDOR_BANK_ACCOUNT_NAME] ?: "",
            vendorAccountNumber = preferences[PreferencesKeys.VENDOR_BANK_ACCOUNT_NUMBER] ?: "",
            vendorBankName = preferences[PreferencesKeys.VENDOR_BANK_NAME] ?: ""
        )
    }

    suspend fun updatePaymentMethod(method: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.PAYMENT_METHOD] = method
        }
    }

    suspend fun updateEmailNotifications(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.EMAIL_NOTIFICATIONS] = bool
        }
    }

    suspend fun updatePushNotifications(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.PUSH_NOTIFICATIONS] = bool
        }
    }

    suspend fun updateStarted(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.ONBOARDED] = bool
        }
    }

    suspend fun updateLoggedIn(
        loggedIn: Boolean,
    ) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.LOGGED_IN] = loggedIn
        }
    }

    suspend fun persistToken(token: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.TOKEN] = token
        }
    }

    suspend fun persistUser(
        userName: String,
        userEmail: String,
        phoneNumber: String,
        companyId: String,
        userId: String,
        fullName: String,
    ) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.USERNAME] = userName
            mutablePreferences[PreferencesKeys.USER_EMAIL] = userEmail
            mutablePreferences[PreferencesKeys.PHONE_NUMBER] = phoneNumber
            mutablePreferences[PreferencesKeys.COMPANY_ID] = companyId
            mutablePreferences[PreferencesKeys.USER_ID] = userId
            mutablePreferences[PreferencesKeys.FULL_NAME] = fullName
        }
    }


    suspend fun updateWalletBalanceVisibility(visibility: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.WALLET_BALANCE_VISIBILITY] = visibility
        }
    }

    suspend fun updateTransactionFilters(
        daysFilter: String,
        mapOfFilters: Map<String, Boolean>
    ) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.TRANSACTION_PERIOD_FILTER] = daysFilter
        }

        mapOfFilters.forEach { (filter, bool) ->
            when (filter) {
                Status.COMPLETED.name -> dataStore.edit { mutablePreferences ->
                    mutablePreferences[PreferencesKeys.COMPLETED_STATUS_FILTER] = bool
                }
                Status.PENDING.name -> dataStore.edit { mutablePreferences ->
                    mutablePreferences[PreferencesKeys.PENDING_STATUS_FILTER] = bool
                }
                Status.FAILED.name -> dataStore.edit { mutablePreferences ->
                    mutablePreferences[PreferencesKeys.FAILED_STATUS_FILTER] = bool
                }
                Product.AGO.name -> dataStore.edit { mutablePreferences ->
                    mutablePreferences[PreferencesKeys.AGO_PRODUCT_FILTER] = bool
                }
                Product.DPK.name -> dataStore.edit { mutablePreferences ->
                    mutablePreferences[PreferencesKeys.DPK_PRODUCT_FILTER] = bool
                }
                Product.PMS.name -> dataStore.edit { mutablePreferences ->
                    mutablePreferences[PreferencesKeys.PMS_PRODUCT_FILTER] = bool
                }
            }
        }
    }

    suspend fun persistProfilePicture(uri: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.PROFILE_PICTURE_URI] = uri
        }
    }

    suspend fun updateStationFilter(filter: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.STATION_FILTER] = filter
        }
    }

    suspend fun updateNotificationPeriodFilter(filter: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.NOTIFICATION_PERIOD_FILTER] = filter
        }
    }

    suspend fun updateVendorData(vendorId: Long, bankAcctName: String, bankAcctNum: String, bankName: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.VENDOR_ID] = vendorId
            mutablePreferences[PreferencesKeys.VENDOR_BANK_ACCOUNT_NAME] = bankAcctName
            mutablePreferences[PreferencesKeys.VENDOR_BANK_ACCOUNT_NUMBER] = bankAcctNum
            mutablePreferences[PreferencesKeys.VENDOR_BANK_NAME] = bankAcctName
        }
    }

    suspend fun updateTransactionPeriodFilter(period: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.TRANSACTION_PERIOD_FILTER] = period
        }
    }
}