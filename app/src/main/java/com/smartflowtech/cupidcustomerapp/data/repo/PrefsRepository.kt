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
        val dateFilter: String = "Today",
        val statusFilter: String = "Completed",
        val productFilter: String = "PMS",
    )

    private object PreferencesKeys {
        val ONBOARDED = booleanPreferencesKey("onboarded")
        val LOGGED_IN = booleanPreferencesKey("loggedIn")
        val USERNAME = stringPreferencesKey("userName")
        val USER_EMAIL = stringPreferencesKey("userEmail")
        val TOKEN = stringPreferencesKey("token")
        val PHONE_NUMBER = stringPreferencesKey("phoneNumber")
        val WALLET_BALANCE_VISIBILITY = booleanPreferencesKey("walletVisibility")
        val DATE_FILTER = stringPreferencesKey("dateFilter")
        val STATUS_FILTER = stringPreferencesKey("statusFilter")
        val PRODUCT_FILTER = stringPreferencesKey("productFilter")
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
            dateFilter = preferences[PreferencesKeys.DATE_FILTER] ?: "Today",
            statusFilter = preferences[PreferencesKeys.STATUS_FILTER] ?: "Completed",
            productFilter = preferences[PreferencesKeys.PRODUCT_FILTER] ?: "PMS",
        )
    }

    suspend fun updateStarted(bool: Boolean) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.ONBOARDED] = bool
        }
    }

    suspend fun updateDateFilter(date: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.DATE_FILTER] = date
        }
    }

    suspend fun updateStatusFilter(status: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.STATUS_FILTER] = status
        }
    }

    suspend fun updateProductFilter(product: String) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[PreferencesKeys.PRODUCT_FILTER] = product
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