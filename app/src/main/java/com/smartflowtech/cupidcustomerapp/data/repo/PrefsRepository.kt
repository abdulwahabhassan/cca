package com.smartflowtech.cupidcustomerapp.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.smartflowtech.cupidcustomerapp.model.Period
import com.smartflowtech.cupidcustomerapp.model.Product
import com.smartflowtech.cupidcustomerapp.model.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class DataStorePrefsRepository @Inject constructor(private val dataStore: DataStore<Preferences>) {

    data class AppConfigPreferences(
        val onBoarded: Boolean = false,
        val loggedIn: Boolean = false,
        val userName: String = "",
        val userEmail: String = "",
        val token: String = "",
        val phoneNumber: String = "",
        val walletBalanceVisibility: Boolean = true,
        val periodFilter: String = Period.TWO_YEARS.name,
        val completedStatusFilter: Boolean = true,
        val failedStatusFilter: Boolean = true,
        val pendingStatusFilter: Boolean = true,
        val dpkProductFilter: Boolean = true,
        val agoProductFilter: Boolean = true,
        val pmsProductFilter: Boolean = true,
        val companyId: String = ""
    )

    private object PreferencesKeys {
        val ONBOARDED = booleanPreferencesKey("onboarded")
        val LOGGED_IN = booleanPreferencesKey("loggedIn")
        val USERNAME = stringPreferencesKey("userName")
        val USER_EMAIL = stringPreferencesKey("userEmail")
        val TOKEN = stringPreferencesKey("token")
        val PHONE_NUMBER = stringPreferencesKey("phoneNumber")
        val WALLET_BALANCE_VISIBILITY = booleanPreferencesKey("walletVisibility")
        val DAYS_FILTER = stringPreferencesKey("periodFilter")
        val COMPLETED_STATUS_FILTER = booleanPreferencesKey("completedStatusFilter")
        val FAILED_STATUS_FILTER = booleanPreferencesKey("failedStatusFilter")
        val PENDING_STATUS_FILTER = booleanPreferencesKey("pendingStatusFilter")
        val DPK_PRODUCT_FILTER = booleanPreferencesKey("dpkProductFilter")
        val PMS_PRODUCT_FILTER = booleanPreferencesKey("pmsProductFilter")
        val AGO_PRODUCT_FILTER = booleanPreferencesKey("agoProductFilter")
        val COMPANY_ID = stringPreferencesKey("companyId")
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
            periodFilter = preferences[PreferencesKeys.DAYS_FILTER] ?: Period.TWO_YEARS.name,
            completedStatusFilter = preferences[PreferencesKeys.COMPLETED_STATUS_FILTER] ?: true,
            failedStatusFilter = preferences[PreferencesKeys.FAILED_STATUS_FILTER] ?: true,
            pendingStatusFilter = preferences[PreferencesKeys.PENDING_STATUS_FILTER] ?: true,
            dpkProductFilter = preferences[PreferencesKeys.DPK_PRODUCT_FILTER] ?: true,
            pmsProductFilter = preferences[PreferencesKeys.PMS_PRODUCT_FILTER] ?: true,
            agoProductFilter = preferences[PreferencesKeys.AGO_PRODUCT_FILTER] ?: true,
            companyId = preferences[PreferencesKeys.COMPANY_ID] ?: "",
        )
    }

    suspend fun updateStarted(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.ONBOARDED] = bool
        }
    }

//    suspend fun updateDaysFilter(string: kotlin.String) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[PreferencesKeys.DAYS_FILTER] = string
//        }
//    }
//
//    suspend fun updateCompletedStatusFilter(bool: Boolean) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[PreferencesKeys.COMPLETED_STATUS_FILTER] = bool
//        }
//    }
//
//    suspend fun updateFailedStatusFilter(bool: Boolean) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[PreferencesKeys.FAILED_STATUS_FILTER] = bool
//        }
//    }
//
//    suspend fun updatePendingStatusFilter(bool: Boolean) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[PreferencesKeys.PENDING_STATUS_FILTER] = bool
//        }
//    }
//
//    suspend fun updateDpkProductFilter(bool: Boolean) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[PreferencesKeys.DPK_PRODUCT_FILTER] = bool
//        }
//    }
//
//    suspend fun updatePmsProductFilter(bool: Boolean) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[PreferencesKeys.PMS_PRODUCT_FILTER] = bool
//        }
//    }
//
//    suspend fun updateAgoProductFilter(bool: Boolean) {
//        dataStore.edit { mutablePreferences ->
//            mutablePreferences[PreferencesKeys.AGO_PRODUCT_FILTER] = bool
//        }
//    }

    suspend fun updateLoggedIn(
        loggedIn: Boolean,
        username: String,
        userEmail: String,
        token: String,
        phoneNumber: String,
        companyId: String
    ) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.LOGGED_IN] = loggedIn
            mutablePreferences[PreferencesKeys.USERNAME] = username
            mutablePreferences[PreferencesKeys.USER_EMAIL] = userEmail
            mutablePreferences[PreferencesKeys.TOKEN] = token
            mutablePreferences[PreferencesKeys.PHONE_NUMBER] = phoneNumber
            mutablePreferences[PreferencesKeys.COMPANY_ID] = companyId
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
            mutablePreferences[PreferencesKeys.DAYS_FILTER] = daysFilter
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
}