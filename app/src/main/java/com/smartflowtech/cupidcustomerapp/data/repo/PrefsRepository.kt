package com.smartflowtech.cupidcustomerapp.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
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
        val daysFilter: Long = 1,
        val completedStatusFilter: Boolean = false,
        val failedStatusFilter: Boolean = false,
        val pendingStatusFilter: Boolean = false,
        val dpkProductFilter: Boolean = false,
        val agoProductFilter: Boolean = false,
        val pmsProductFilter: Boolean = false,
    )

    private object PreferencesKeys {
        val ONBOARDED = booleanPreferencesKey("onboarded")
        val LOGGED_IN = booleanPreferencesKey("loggedIn")
        val USERNAME = stringPreferencesKey("userName")
        val USER_EMAIL = stringPreferencesKey("userEmail")
        val TOKEN = stringPreferencesKey("token")
        val PHONE_NUMBER = stringPreferencesKey("phoneNumber")
        val WALLET_BALANCE_VISIBILITY = booleanPreferencesKey("walletVisibility")
        val DAYS_FILTER = longPreferencesKey("daysFilter")
        val COMPLETED_STATUS_FILTER = booleanPreferencesKey("completedStatusFilter")
        val FAILED_STATUS_FILTER = booleanPreferencesKey("failedStatusFilter")
        val PENDING_STATUS_FILTER = booleanPreferencesKey("pendingStatusFilter")
        val DPK_PRODUCT_FILTER = booleanPreferencesKey("dpkProductFilter")
        val PMS_PRODUCT_FILTER = booleanPreferencesKey("pmsProductFilter")
        val AGO_PRODUCT_FILTER = booleanPreferencesKey("agoProductFilter")
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
            daysFilter = preferences[PreferencesKeys.DAYS_FILTER] ?: 1,
            completedStatusFilter = preferences[PreferencesKeys.COMPLETED_STATUS_FILTER] ?: false,
            failedStatusFilter = preferences[PreferencesKeys.FAILED_STATUS_FILTER] ?: false,
            pendingStatusFilter = preferences[PreferencesKeys.PENDING_STATUS_FILTER] ?: false,
            dpkProductFilter = preferences[PreferencesKeys.DPK_PRODUCT_FILTER] ?: false,
            pmsProductFilter = preferences[PreferencesKeys.PMS_PRODUCT_FILTER] ?: false,
            agoProductFilter = preferences[PreferencesKeys.AGO_PRODUCT_FILTER] ?: false,
        )
    }

    suspend fun updateStarted(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.ONBOARDED] = bool
        }
    }

    suspend fun updateDaysFilter(days: Long) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.DAYS_FILTER] = days
        }
    }

    suspend fun updateCompletedStatusFilter(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.COMPLETED_STATUS_FILTER] = bool
        }
    }

    suspend fun updateFailedStatusFilter(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.FAILED_STATUS_FILTER] = bool
        }
    }

    suspend fun updatePendingStatusFilter(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.PENDING_STATUS_FILTER] = bool
        }
    }

    suspend fun updateDpkProductFilter(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.DPK_PRODUCT_FILTER] = bool
        }
    }

    suspend fun updatePmsProductFilter(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.PMS_PRODUCT_FILTER] = bool
        }
    }

    suspend fun updateAgoProductFilter(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.AGO_PRODUCT_FILTER] = bool
        }
    }

    suspend fun updateLoggedIn(
        loggedIn: Boolean,
        username: String,
        userEmail: String,
        token: String,
        phoneNumber: String
    ) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.LOGGED_IN] = loggedIn
            mutablePreferences[PreferencesKeys.USERNAME] = username
            mutablePreferences[PreferencesKeys.USER_EMAIL] = userEmail
            mutablePreferences[PreferencesKeys.TOKEN] = token
            mutablePreferences[PreferencesKeys.PHONE_NUMBER] = phoneNumber
        }
    }

    suspend fun updateWalletBalanceVisibility(visibility: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.WALLET_BALANCE_VISIBILITY] = visibility
        }
    }
}